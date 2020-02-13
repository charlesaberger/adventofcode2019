package thebergers.adventofcode2019.intcodecomputer;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.core.Appender;

public class IntcodeComputer implements IntcodeComputerInterface {

	private static final Logger LOG = LoggerFactory.getLogger(IntcodeComputer.class);
	
	private Program program;
	
	private final String name;
	
	private final Integer sequenceNumber;
	
	private boolean terminated = false;
	
	private final InstructionBuilder instructionBuilder;
	
	private Long relativeBase = 0L;
	
	private Long instructionPointer = 0L;
	
	private BlockingQueue<Long> input = new LinkedBlockingQueue<>();
	
	private PropertyChangeSupport support;
	
	private List<Long> output = new ArrayList<>();
	
	public IntcodeComputer(String opcodesStr) {
		this.sequenceNumber = 1;
		this.name = "IntcodeComputer";
		this.program = new Program();
		program.initialise(opcodesStr);
		this.instructionBuilder = new InstructionBuilder();
		this.support = new PropertyChangeSupport(this);
	}
	
	public IntcodeComputer(Integer sequenceNumber, String name, String opcodesStr) {
		this.sequenceNumber = sequenceNumber;
		this.name = name;
		this.program = new Program();
		program.initialise(opcodesStr);
		this.instructionBuilder = new InstructionBuilder();
		this.support = new PropertyChangeSupport(this);
	}
	
	@Override
	public IntcodeComputerResult processOpcodes() {
		parseInstructions();
		return new IntcodeComputerResult(sequenceNumber, name, getResult(), output, terminated);
	}

	public void setCode(Long index, Long value) {
		program.setOpcode(index, value);
	}

	public String getName() {
		return name;
	}

	private String getResult() {
		return program.getResult();
	}
	
	public void reset() {
		program.reset();
		this.instructionPointer = 0L;
	}
	
	public void reset(String opcodesStr) {
		program.reset();
		program.initialise(opcodesStr);
		this.instructionPointer = 0L;
	}

	@Override
	public void addInput(Long input) {
		this.input.add(input);
	}
	
	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		support.addPropertyChangeListener(listener);
	}
	
	@Override
	public void run() {
		parseInstructions();
		IntcodeComputerResult computedResult = new IntcodeComputerResult(sequenceNumber, name,
				getResult(), output, terminated);
		if (support.getPropertyChangeListeners().length > 0) {
			LOG.debug("{} sending output {}...", name, computedResult);
			support.firePropertyChange("result", IntcodeComputerResult.errorInstance(), computedResult);
		}
	}

	private void parseInstructions() {
		while (true) {
			LOG.debug("{}", program.getResult());
			Instruction instruction = instructionBuilder.build(instructionPointer);
			LOG.debug("{}: {}", name, instruction);
			instruction.process();
			if (instruction.isTerminate()) {
				return;
			}
			instructionPointer = instruction.getNextInstructionPointer(instructionPointer);
			LOG.debug("nextInstructionPointer={}", instructionPointer);
		}
	}
	
	private class Program {
		
		private  List<Long> opcodes;
		
		Program() {
			this.opcodes = new ArrayList<>();
		}
		
		void initialise(String opcodesStr) {
			String[] values = opcodesStr.split(",");
			List<String> valuesList = Arrays.asList(values);
			program.setOpcode(valuesList.stream().map(Long::parseLong).collect(Collectors.toList()));
		}
		
		void setOpcode(List<Long> opcodes) {
			this.opcodes.addAll(opcodes);
		}
		
		void setOpcode(Long addressLong, Long value) {
			int address = convertLongToInt(addressLong);
			checkMemorySize(address);
			this.opcodes.set(address, value);
		}

		Long getOpcode(Long instructionPointerLong) {
			int instructionPointerInt = convertLongToInt(instructionPointerLong);
			checkMemorySize(instructionPointerInt);
			return opcodes.get(instructionPointerInt);
		}
		
		void reset() {
			opcodes.clear();
		}
		
		private void checkMemorySize(int index) {
			if (index >= opcodes.size()) {
				extendMemoryTo(index);
			}
		}
		private void extendMemoryTo(Integer instructionPointer) {
			IntStream.range(0, instructionPointer - opcodes.size() + 1)
			.forEach(value -> { boolean added = opcodes.add(0L); });
		}
		
		private int convertLongToInt(Long index) {
			if (index > Integer.MAX_VALUE) {
				throw new IllegalArgumentException(String.format("Index value %d is too large!", index));
			}
			return Math.toIntExact(index);
		}
		
		String getResult() {
			return opcodes.stream().map(n -> n.toString()).collect(Collectors.joining(","));
		}
	}

	enum OpCode {
		ADD(1, 3),
		MULTIPLY(2, 3),
		INPUT(3, 1),
		OUTPUT(4, 1),
		JUMPIFTRUE(5, 2),
		JUMPIFFALSE(6, 2),
		LESSTHAN(7, 3),
		EQUALS(8, 3),
		UPDATERELATIVEBASE(9, 1),
		TERMINATE(99, 0);
		
		private final int value;
		
		private final int numParameters;
		
		public int getValue() {
			return value;
		}
		
		public int getNumParameters() {
			return numParameters;
		}

		private OpCode(int value, int numParameters) {
			this.value = value;
			this.numParameters = numParameters;
		}
		
		public static OpCode getInstance(Integer value) {
			
			switch (value) {
			case 1:
				return ADD;
			case 2:
				return MULTIPLY;
			case 3:
				return INPUT;
			case 4:
				return OUTPUT;
			case 5:
				return JUMPIFTRUE;
			case 6:
				return JUMPIFFALSE;
			case 7:
				return LESSTHAN;
			case 8:
				return EQUALS;
			case 9:
				return UPDATERELATIVEBASE;
			case 99:
				return TERMINATE;
			default:
				throw new IllegalArgumentException(String.format("Unknown OpCode: %d", value));
			}
		}
	}
	
	enum ParameterMode {
		POSITIONAL(0),
		IMMEDIATE(1),
		RELATIVE(2);
		
		private final int value;
		
		public int getValue() {
			return value;
		}
		
		private ParameterMode(int value) {
			this.value = value;
		}
		
		public static ParameterMode getInstance(Integer value) {
			switch(value) {
			case 0:
				return POSITIONAL;
			case 1:
				return IMMEDIATE;
			case 2:
				return RELATIVE;
			default:
				throw new IllegalArgumentException(String.format("Unknown ParameterMode: %s", value));
			}
		}
	}
	
	public Parameter getParameterInstance(ParameterMode mode, Long value) {
		switch(mode) {
		case POSITIONAL:
			return new PositionalParameter(value);
		case IMMEDIATE:
			return new ImmediateParameter(value);
		case RELATIVE:
			return new RelativeParameter(value);
		default:
			throw new IllegalArgumentException(String.format("Unknown ParameterMode: %s",  mode));
		}
	}
	
	abstract class Parameter {
		
		protected ParameterMode mode;
		
		protected final Long index;
		
		private Parameter(ParameterMode mode, Long index) {
			this.mode = mode;
			this.index = index;
		}
		
		public ParameterMode getMode() {
			return mode;
		}
		
		public Long getIndex() {
			return index;
		}
		
		public abstract Long getAddress();

		public Long getValue() {
			return program.getOpcode(getAddress());
		}

		@Override
		public String toString() {
			return "Parameter [mode=" + mode + ", index=" + index + ", address=" + getAddress() +
					", value()=" + getValue() + "]" + System.lineSeparator();
		}
	}
	
	class PositionalParameter extends Parameter {
		
		PositionalParameter(Long index) {
			super(ParameterMode.POSITIONAL, index);
		}

		@Override
		public Long getAddress() {
			return program.getOpcode(index);
		}
	}
	
	class ImmediateParameter extends Parameter {
		
		ImmediateParameter(Long index) {
			super(ParameterMode.IMMEDIATE, index);
		}

		@Override
		public Long getAddress() {
			return index;
		}
	}
	
	class RelativeParameter extends PositionalParameter {
		
		RelativeParameter(Long index) {
			super(index);
			this.mode = ParameterMode.RELATIVE;
		}

		@Override
		public Long getAddress() {
			return super.getAddress() + relativeBase;
		}

		@Override
		public String toString() {
			return "Parameter [mode=" + super.mode + ", index=" + index + ", super.getAddress()=" + super.getAddress() + 
					", relativeBase=" + relativeBase + ", this.getAddress()=" + getAddress() +", value()=" + getValue() +
					"]" + System.lineSeparator();
		}
	}
	
	class InstructionBuilder {
		Instruction build(Long instructionPointer) {
			Long instructionInt = program.getOpcode(instructionPointer);
			char[] instructionChars = instructionInt.toString().toCharArray();
			OpCode opcode = parseOpcode(instructionChars);
			Instruction instruction = getInstruction(opcode, instructionPointer);
			instruction.initialiseParameters();
			return instruction;
		}

		private OpCode parseOpcode(char[] instructionChars) {
			String opCodeStr = new String(instructionChars);
			int instructionCharsLength = instructionChars.length;
			if (instructionCharsLength >= 2) {
				opCodeStr = opCodeStr.substring(instructionCharsLength - 2);
			}
			return OpCode.getInstance(Integer.parseInt(opCodeStr));
		}
		
		private Instruction getInstruction(OpCode opcode, Long instructionPointer) {
			switch (opcode) {
			case ADD:
				return new AddInstruction(opcode, instructionPointer);
			case MULTIPLY:
				return new MultiplyInstruction(opcode, instructionPointer);
			case INPUT:
				return new InputInstruction(opcode, instructionPointer);
			case OUTPUT:
				return new OutputInstruction(opcode, instructionPointer);
			case JUMPIFTRUE:
				return new JumpIfTrueInstruction(opcode, instructionPointer);
			case JUMPIFFALSE:
				return new JumpIfFalseInstruction(opcode, instructionPointer);
			case LESSTHAN:
				return new LessThanInstruction(opcode, instructionPointer);
			case EQUALS:
				return new EqualsInstruction(opcode, instructionPointer);
			case UPDATERELATIVEBASE:
				return new UpdateRelativeBaseInstruction(opcode, instructionPointer);
			case TERMINATE:
				return new TerminateInstruction(opcode, instructionPointer);
			default:
				throw new IllegalArgumentException(String.format("Unknown OpCode: %s", opcode));
			}
		}
	}

	abstract class Instruction {

		protected final Long index;
		
		protected final OpCode opcode;
		
		protected List<Parameter> parameters;
		
		protected void process() {
			evaluate();
			outputResult();
		}
		
		public boolean isTerminate() {
			return opcode.equals(OpCode.TERMINATE);
		}

		protected abstract void initialiseParameters();
		
		protected abstract void evaluate();
		
		protected abstract Long getResultPosition();
		
		protected abstract void outputResult(); 
		
		protected Long getNextInstructionPointer(Long instructionPointer) {
			return instructionPointer + opcode.numParameters + 1;
		}
		
		protected Instruction(OpCode opcode, Long index) {
			this.index = index;
			this.opcode = opcode;
		}
		
		protected List<ParameterMode> getParameterModes() {
			Long instructionInt = program.getOpcode(index);
			char[] instructionChars = instructionInt.toString().toCharArray();
			List<ParameterMode> modes = new ArrayList<>();
			int length = instructionChars.length;
			if (length <= 2) {
				return modes;
			}
			int parameterCount = length - 2;
			while (parameterCount > 0) {
				Integer modeInt = Character.getNumericValue(instructionChars[parameterCount - 1]);
				modes.add(ParameterMode.getInstance(modeInt));
				parameterCount--;
			}
			return modes;
		}
		
		protected void setParameters(List<ParameterMode> parameterModes) {
			long paramIndex = index;
			parameters = new ArrayList<>();
			for (ParameterMode mode : parameterModes) {
				paramIndex++;
				parameters.add(getParameterInstance(mode, paramIndex));
			}
		}

		@Override
		public String toString() {
			return "Instruction [index=" + index + ", val@index=" + program.getOpcode(index) +
					", opcode=" + opcode + ", parameters=" + System.lineSeparator() + parameters + "]";
		}
		
	}

	public class AddInstruction extends Instruction {
		
		private Long result;

		public AddInstruction(OpCode opcode, Long index) {
			super(opcode, index);
		}

		@Override
		protected void initialiseParameters() {
			List<ParameterMode> parameterModes = getParameterModes();
			for (int i = 1; i <= opcode.numParameters; i++) {
				if (i > parameterModes.size()) {
					parameterModes.add(ParameterMode.POSITIONAL);
				}
			}
			setParameters(parameterModes);
		}
		
		@Override
		protected void evaluate() {
			Long noun = parameters.get(0).getValue();
			Long verb = parameters.get(1).getValue();
			result = noun + verb;
		}

		@Override
		protected Long getResultPosition() {
			return parameters.get(2).getAddress();
		}

		@Override
		protected void outputResult() {
			Long resultPosition = getResultPosition();
			LOG.debug("Writing value {} to position {}", result, resultPosition);
			program.setOpcode(resultPosition, result);
		}
	}

	public class MultiplyInstruction extends Instruction {
		
		private Long result;

		public MultiplyInstruction(OpCode opcode, Long index) {
			super(opcode, index);
		}
		
		@Override
		protected void evaluate() {
			Long noun = parameters.get(0).getValue();
			Long verb = parameters.get(1).getValue();
			result = noun * verb;
		}

		@Override
		protected void initialiseParameters() {
			List<ParameterMode> parameterModes = getParameterModes();
			for (int i = 1; i <= opcode.numParameters; i++) {
				if (i > parameterModes.size()) {
					parameterModes.add(ParameterMode.POSITIONAL);
				}
			}
			setParameters(parameterModes);
		}

		@Override
		protected Long getResultPosition() {
			return parameters.get(2).getAddress();
		}

		@Override
		protected void outputResult() {
			program.setOpcode(getResultPosition(), result);
		}
	}
	
	public class InputInstruction extends Instruction {
		
		private Long inputValue;
		
		protected InputInstruction(OpCode opcode, Long index) {
			super(opcode, index);
		}

		@Override
		protected void initialiseParameters() {
			List<ParameterMode> parameterModes = getParameterModes();
			if (parameterModes.isEmpty()) {
				parameterModes.add(ParameterMode.POSITIONAL);
			}
			setParameters(parameterModes);
		}

		@Override
		protected void evaluate() {
			LOG.debug("{}: looking for input", name);
			try {
				inputValue = input.take();
				LOG.debug("{} received value {}", name, inputValue);
			} catch (InterruptedException e) {
				LOG.warn("{}", e);
			}
		}

		@Override
		protected Long getResultPosition() {
			return parameters.get(0).getAddress();
		}

		@Override
		protected void outputResult() {
			Long resultPosition = getResultPosition();
			LOG.debug("Writing value {} to position {}", inputValue, resultPosition);
			program.setOpcode(resultPosition, inputValue);
		}
	}
	
	public class OutputInstruction extends Instruction {
		
		private Long result;
		
		protected OutputInstruction(OpCode opcode, Long index) {
			super(opcode, index);
		}

		@Override
		protected void initialiseParameters() {
			List<ParameterMode> parameterModes = getParameterModes();
			if (parameterModes.isEmpty()) {
				parameterModes.add(ParameterMode.POSITIONAL);
			}
			setParameters(parameterModes);
		}

		@Override
		protected void evaluate() {
			result = parameters.get(0).getValue();
		}

		@Override
		protected Long getResultPosition() {
			return parameters.get(0).getAddress();
		}

		@Override
		protected void outputResult() {
			output.add(result);
			LOG.debug("{} outputting {}", name, output);
			if (support.getPropertyChangeListeners().length > 0) {
				IntcodeComputerResult computedResult =
						new IntcodeComputerResult(sequenceNumber, name, getResult(), output, terminated);
				LOG.debug("{} sending output {}...", name, computedResult);
				support.firePropertyChange("result", IntcodeComputerResult.errorInstance(), computedResult);
			}
		}
	}
	
	public class JumpIfTrueInstruction extends Instruction {

		protected JumpIfTrueInstruction(OpCode opcode, Long index) {
			super(opcode, index);
		}

		@Override
		protected void initialiseParameters() {
			List<ParameterMode> parameterModes = getParameterModes();
			for (int i = 1; i <= opcode.numParameters; i++) {
				if (i > parameterModes.size()) {
					parameterModes.add(ParameterMode.POSITIONAL);
				}
			}
			setParameters(parameterModes);
		}

		@Override
		protected void process() {
			// Override this method and do nothing
		}

		@Override
		protected Long getNextInstructionPointer(Long instructionPointer) {
			Parameter param1 = parameters.get(0);
			Long param1Value = param1.getValue();
			Parameter param2 = parameters.get(1);
			Long param2Value = param2.getValue();
			if (param1Value > 0) {
				LOG.debug("{} > 0: jumping to {}", param1Value, param2Value);
				return param2Value;
			}
			Long nextPtr = super.getNextInstructionPointer(instructionPointer);
			LOG.debug("{} <= 0: next instruction pointer is: {}", param1Value, nextPtr);
			return nextPtr;
		}

		@Override
		protected void evaluate() {
			// We do not use this method for this instruction
		}

		@Override
		protected Long getResultPosition() {
			// We do not use this method for this instruction
			return 0L;
		}

		@Override
		protected void outputResult() {
			// no output from this instruction
		}
	}
	
	public class JumpIfFalseInstruction extends Instruction {

		protected JumpIfFalseInstruction(OpCode opcode, Long index) {
			super(opcode, index);
		}

		@Override
		protected void initialiseParameters() {
			List<ParameterMode> parameterModes = getParameterModes();
			for (int i = 1; i <= opcode.numParameters; i++) {
				if (i > parameterModes.size()) {
					parameterModes.add(ParameterMode.POSITIONAL);
				}
			}
			setParameters(parameterModes);
		}

		@Override
		protected void process() {
			// Override this method and do nothing
		}

		@Override
		protected Long getNextInstructionPointer(Long instructionPointer) {
			Parameter param1 = parameters.get(0);
			Long param1Value = param1.getValue();
			Parameter param2 = parameters.get(1);
			Long param2Value = param2.getValue();
			if (param1Value.equals(0L)) {
				LOG.debug("{} = 0, jumping to instruction {}", param1Value, param2Value);
				return param2Value;
			}
			Long nextPtr = super.getNextInstructionPointer(instructionPointer);
			LOG.debug("{} != 0: next instruction is {}", param1Value, nextPtr);
			return nextPtr;
		}

		@Override
		protected void evaluate() {
			// We do not use this method for this instruction
		}

		@Override
		protected Long getResultPosition() {
			// We do not use this method for this instruction
			return 0L;
		}

		@Override
		protected void outputResult() {
			// no output from this instruction
		}
	}
	
	class LessThanInstruction extends Instruction {
		
		private Long result;

		protected LessThanInstruction(OpCode opcode, Long index) {
			super(opcode, index);
		}

		@Override
		protected void initialiseParameters() {
			List<ParameterMode> parameterModes = getParameterModes();
			for (int i = 1; i <= opcode.numParameters; i++) {
				if (i > parameterModes.size()) {
					parameterModes.add(ParameterMode.POSITIONAL);
				}
			}
			setParameters(parameterModes);
		}

		@Override
		protected void evaluate() {
			Long value1 = parameters.get(0).getValue();
			Long value2 = parameters.get(1).getValue();
			result = value1 < value2 ? 1L : 0L;
		}

		@Override
		protected Long getResultPosition() {
			return parameters.get(2).getAddress();
		}

		@Override
		protected void outputResult() {
			Long resultPosition = getResultPosition();
			LOG.debug("Writing value {} to position {}", result, resultPosition);
			program.setOpcode(resultPosition, result);
		}
	}
	
	class EqualsInstruction extends Instruction {
		
		private Long result;

		protected EqualsInstruction(OpCode opcode, Long index) {
			super(opcode, index);
		}

		@Override
		protected void initialiseParameters() {
			List<ParameterMode> parameterModes = getParameterModes();
			for (int i = 1; i <= opcode.numParameters; i++) {
				if (i > parameterModes.size()) {
					parameterModes.add(ParameterMode.POSITIONAL);
				}
			}
			setParameters(parameterModes);
		}

		@Override
		protected void evaluate() {
			Long value1 = parameters.get(0).getValue();
			Long value2 = parameters.get(1).getValue();
			result = value1.equals(value2) ? 1L : 0;
		}

		@Override
		protected Long getResultPosition() {
			return parameters.get(2).getAddress();
		}

		@Override
		protected void outputResult() {
			Long resultPosition = getResultPosition();
			LOG.debug("Writing value {} to position {}", result, resultPosition);
			program.setOpcode(resultPosition, result);
		}
	}
	
	public class UpdateRelativeBaseInstruction extends Instruction {
		
		private Long newRelativeBase;

		protected UpdateRelativeBaseInstruction(OpCode opcode, Long index) {
			super(opcode, index);
		}

		@Override
		protected void initialiseParameters() {
			List<ParameterMode> parameterModes = getParameterModes();
			for (int i = 1; i <= opcode.numParameters; i++) {
				if (i > parameterModes.size()) {
					parameterModes.add(ParameterMode.POSITIONAL);
				}
			}
			setParameters(parameterModes);
		}

		@Override
		protected void evaluate() {
			Long paramValue =  parameters.get(0).getValue();
			newRelativeBase = relativeBase + paramValue;
			LOG.debug("Change relativeBase from {} to {}", relativeBase, newRelativeBase);
		}

		@Override
		protected Long getResultPosition() {
			return null;
		}

		@Override
		protected void outputResult() {
			relativeBase = newRelativeBase;
		}
	}
	
	public class TerminateInstruction extends Instruction {

		protected TerminateInstruction(OpCode opcode, Long index) {
			super(opcode, index);
		}

		@Override
		protected void initialiseParameters() {
			// do nothing
		}

		@Override
		protected void evaluate() {
			terminated = true;
		}

		@Override
		protected Long getResultPosition() {
			return null;
		}

		@Override
		protected void outputResult() {
			// no output from this instruction
		}
	}
}
