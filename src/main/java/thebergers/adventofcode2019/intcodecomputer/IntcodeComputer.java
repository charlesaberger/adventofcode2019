package thebergers.adventofcode2019.intcodecomputer;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IntcodeComputer implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(IntcodeComputer.class);
	
	private final List<Integer> opcodes;
	
	private final String name;
	
	private final Integer sequenceNumber;
	
	private boolean terminated = false;
	
	private final InstructionBuilder instructionBuilder;
	
	private Integer relativeBase = 0;
	
	private Integer instructionPointer = 0;
	
	private BlockingQueue<Integer> input = new LinkedBlockingQueue<>();
	
	private PropertyChangeSupport support;
	
	private Integer output;
	
	public IntcodeComputer(String opcodesStr) {
		this.sequenceNumber = 1;
		this.name = "IntcodeComputer";
		this.opcodes = initialise(opcodesStr);
		this.instructionBuilder = new InstructionBuilder();
	}
	
	public IntcodeComputer(Integer sequenceNumber, String name, String opcodesStr) {
		this.sequenceNumber = sequenceNumber;
		this.name = name;
		this.opcodes = initialise(opcodesStr);
		this.instructionBuilder = new InstructionBuilder();
	}
	
	public IntcodeComputerResult processOpcodes() {
		parseInstructions();
		return new IntcodeComputerResult(sequenceNumber, name, getResult(), output, terminated);
	}

	public void setCode(int index, Integer value) {
		opcodes.set(index, value);
	}
	
	public String getName() {
		return name;
	}

	private String getResult() {
		return opcodes.stream().map(n -> n.toString()).collect(Collectors.joining(","));
	}
	
	public void reset() {
		opcodes.clear();
		this.instructionPointer = 0;
	}
	
	public void reset(String opcodesStr) {
		reset();
		this.opcodes.addAll(initialise(opcodesStr));
	}

	public void addInput(Integer input) {
		this.input.add(input);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		support.addPropertyChangeListener(listener);
	}
	
	@Override
	public void run() {
		parseInstructions();
		IntcodeComputerResult computedResult = new IntcodeComputerResult(sequenceNumber, name,
				getResult(), output, terminated);
		if (support.getPropertyChangeListeners().length > 0) {
			LOG.info("{} sending output {}...", name, computedResult);
			support.firePropertyChange("result", IntcodeComputerResult.errorInstance(), computedResult);
		}
	}

	private List<Integer> initialise(String opcodes) {
		this.support = new PropertyChangeSupport(this);
		String[] values = opcodes.split(",");
		List<String> valuesList = Arrays.asList(values);
		return valuesList.stream().map(Integer::parseInt).collect(Collectors.toList());
	}

	private void parseInstructions() {
		while (true) {
			//LOG.info("{}", opcodes);
			Instruction instruction = instructionBuilder.build(instructionPointer);
			//LOG.info("{}: {}", name, instruction);
			instruction.process();
			if (instruction.isTerminate()) {
				return;
			}
			instructionPointer = instruction.getNextInstructionPointer(instructionPointer);
			//LOG.info("nextInstructionPointer={}", instructionPointer);
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
	
	public Parameter getParameterInstance(ParameterMode mode, Integer value) {
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
		
		private final ParameterMode mode;
		
		protected final Integer address;
		
		private Parameter(ParameterMode mode, Integer address) {
			this.mode = mode;
			this.address = address;
		}
		
		public ParameterMode getMode() {
			return mode;
		}
		
		public Integer getAddress() {
			return address;
		}
		
		public Integer getValueAtAddress() {
			return opcodes.get(address);
		}
		
		abstract Integer getValue();

		@Override
		public String toString() {
			return "Parameter [mode=" + mode + ", addr=" + address + ", val@addr=" + getValueAtAddress() +
					", value()=" + getValue() + "]";
		}
	}
	
	class PositionalParameter extends Parameter {
		
		PositionalParameter(Integer address) {
			super(ParameterMode.POSITIONAL, address);
		}

		@Override
		Integer getValue() {
			Integer valueAtAddress = getValueAtAddress();
			return opcodes.get(valueAtAddress);
		}
	}
	
	class ImmediateParameter extends Parameter {
		
		ImmediateParameter(Integer address) {
			super(ParameterMode.IMMEDIATE, address);
		}

		@Override
		Integer getValue() {
			return getValueAtAddress();
		}
	}
	
	class RelativeParameter extends Parameter {
		
		RelativeParameter(Integer address) {
			super(ParameterMode.RELATIVE, address + relativeBase);
		}

		@Override
		Integer getValue() {
			Integer valueAtAddress = getValueAtAddress();
			return opcodes.get(valueAtAddress);
		}
	}
	
	class InstructionBuilder {
		Instruction build(int index) {
			Integer instructionInt = opcodes.get(index);
			char[] instructionChars = instructionInt.toString().toCharArray();
			OpCode opcode = parseOpcode(instructionChars);
			Instruction instruction = getInstruction(opcode, index);
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
		
		private Instruction getInstruction(OpCode opcode, int index) {
			switch (opcode) {
			case ADD:
				return new AddInstruction(opcode, index);
			case MULTIPLY:
				return new MultiplyInstruction(opcode, index);
			case INPUT:
				return new InputInstruction(opcode, index);
			case OUTPUT:
				return new OutputInstruction(opcode, index);
			case JUMPIFTRUE:
				return new JumpIfTrueInstruction(opcode, index);
			case JUMPIFFALSE:
				return new JumpIfFalseInstruction(opcode, index);
			case LESSTHAN:
				return new LessThanInstruction(opcode, index);
			case EQUALS:
				return new EqualsInstruction(opcode, index);
			case UPDATERELATIVEBASE:
				return new UpdateRelativeBaseInstruction(opcode, index);
			case TERMINATE:
				return new TerminateInstruction(opcode, index);
			default:
				throw new IllegalArgumentException(String.format("Unknown OpCode: %s", opcode));
			}
		}
	}

	abstract class Instruction {

		protected final int index;
		
		protected final OpCode opcode;
		
		protected List<Parameter> parameters;
		
		protected Integer result;
		
		protected void process() {
			result = calculate();
			outputResult();
		}
		
		public boolean isTerminate() {
			return opcode.equals(OpCode.TERMINATE);
		}

		protected abstract void initialiseParameters();
		
		protected abstract Integer calculate();
		
		protected abstract Integer getResultPosition();
		
		protected abstract void outputResult(); 
		
		protected Integer getNextInstructionPointer(int instructionPointer) {
			return instructionPointer + opcode.numParameters + 1;
		}
		
		protected Instruction(OpCode opcode, int index) {
			this.index = index;
			this.opcode = opcode;
		}
		
		protected List<ParameterMode> getParameterModes() {
			Integer instructionInt = opcodes.get(index);
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
			int paramIndex = index;
			parameters = new ArrayList<>();
			for (ParameterMode mode : parameterModes) {
				paramIndex++;
				parameters.add(getParameterInstance(mode, paramIndex));
			}
		}

		@Override
		public String toString() {
			return "Instruction [index=" + index + ", val@index=" + opcodes.get(index) + ", opcode=" + opcode + ", parameters=" + parameters + "]";
		}
		
	}

	public class AddInstruction extends Instruction {

		public AddInstruction(OpCode opcode, int index) {
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
		protected Integer calculate() {
			Integer noun = parameters.get(0).getValue();
			Integer verb = parameters.get(1).getValue();
			return noun + verb;
		}

		@Override
		protected Integer getResultPosition() {
			return opcodes.get(parameters.get(2).getAddress());
		}

		@Override
		protected void outputResult() {
			Integer resultPosition = getResultPosition();
			//LOG.info("Writing value {} to position {}", result, resultPosition);
			opcodes.set(resultPosition, result);
		}
	}

	public class MultiplyInstruction extends Instruction {

		public MultiplyInstruction(OpCode opcode, int index) {
			super(opcode, index);
		}
		
		@Override
		protected Integer calculate() {
			Integer noun = parameters.get(0).getValue();
			Integer verb = parameters.get(1).getValue();
			return noun * verb;
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
		protected Integer getResultPosition() {
			return opcodes.get(parameters.get(2).getAddress());
		}

		@Override
		protected void outputResult() {
			opcodes.set(getResultPosition(), result);
		}
	}
	
	public class InputInstruction extends Instruction {
		
		protected InputInstruction(OpCode opcode, int index) {
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
		protected Integer calculate() {
			LOG.info("{}: looking for input", name);
			try {
				Integer val = input.take();
				LOG.info("{} received value {}", name, val);
				return val;
			} catch (InterruptedException e) {
				LOG.warn("{}", e);
			}
			return null;
		}

		@Override
		protected Integer getResultPosition() {
			return opcodes.get(parameters.get(0).getAddress());
		}

		@Override
		protected void outputResult() {
			Integer resultPosition = getResultPosition();
			//LOG.info("Writing value {} to position {}", result, resultPosition);
			opcodes.set(resultPosition, result);
		}
	}
	
	public class OutputInstruction extends Instruction {
		
		protected OutputInstruction(OpCode opcode, int index) {
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
		protected Integer calculate() {
			return parameters.get(0).getValue();
		}

		@Override
		protected Integer getResultPosition() {
			return opcodes.get(parameters.get(0).getAddress());
		}

		@Override
		protected void outputResult() {
			output = result;
			//LOG.info("{} outputting {}", name, output);
			if (support.getPropertyChangeListeners().length > 0) {
				IntcodeComputerResult computedResult =
						new IntcodeComputerResult(sequenceNumber, name, getResult(), output, terminated);
				//LOG.info("{} sending output {}...", name, computedResult);
				support.firePropertyChange("result", IntcodeComputerResult.errorInstance(), computedResult);
			}
		}
	}
	
	public class JumpIfTrueInstruction extends Instruction {

		protected JumpIfTrueInstruction(OpCode opcode, int index) {
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
		protected Integer getNextInstructionPointer(int instructionPointer) {
			Parameter param1 = parameters.get(0);
			Integer param1Value = param1.getValue();
			Parameter param2 = parameters.get(1);
			Integer param2Value = param2.getValue();
			if (param1Value > 0) {
				//LOG.info("{} > 0: jumping to {}", param1Value, param2Value);
				return param2Value;
			}
			Integer nextPtr = super.getNextInstructionPointer(instructionPointer);
			//LOG.info("{} <= 0: next instruction pointer is: {}", param1Value, nextPtr);
			return nextPtr;
		}

		@Override
		protected Integer calculate() {
			// We do not use this method for this instruction
			return 0;
		}

		@Override
		protected Integer getResultPosition() {
			// We do not use this method for this instruction
			return 0;
		}

		@Override
		protected void outputResult() {
			// no output from this instruction
		}
	}
	
	public class JumpIfFalseInstruction extends Instruction {

		protected JumpIfFalseInstruction(OpCode opcode, int index) {
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
		protected Integer getNextInstructionPointer(int instructionPointer) {
			Parameter param1 = parameters.get(0);
			Integer param1Value = param1.getValue();
			Parameter param2 = parameters.get(1);
			Integer param2Value = param2.getValue();
			if (param1Value.equals(0)) {
				//LOG.info("{} = 0, jumping to instruction {}", param1Value, param2Value);
				return param2Value;
			}
			Integer nextPtr = super.getNextInstructionPointer(instructionPointer);
			//LOG.info("{} != 0: next instruction is {}", param1Value, nextPtr);
			return nextPtr;
		}

		@Override
		protected Integer calculate() {
			// We do not use this method for this instruction
			return 0;
		}

		@Override
		protected Integer getResultPosition() {
			// We do not use this method for this instruction
			return 0;
		}

		@Override
		protected void outputResult() {
			// no output from this instruction
		}
	}
	
	class LessThanInstruction extends Instruction {

		protected LessThanInstruction(OpCode opcode, int index) {
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
		protected Integer calculate() {
			Integer value1 = parameters.get(0).getValue();
			Integer value2 = parameters.get(1).getValue();
			return value1 < value2 ? 1 : 0;
		}

		@Override
		protected Integer getResultPosition() {
			return parameters.get(2).getValueAtAddress();
		}

		@Override
		protected void outputResult() {
			Integer resultPosition = getResultPosition();
			//LOG.info("Writing value {} to position {}", result, resultPosition);
			opcodes.set(resultPosition, result);
		}
	}
	
	class EqualsInstruction extends Instruction {

		protected EqualsInstruction(OpCode opcode, int index) {
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
		protected Integer calculate() {
			Integer value1 = parameters.get(0).getValue();
			Integer value2 = parameters.get(1).getValue();
			return value1.equals(value2) ? 1 : 0;
		}

		@Override
		protected Integer getResultPosition() {
			return opcodes.get(parameters.get(2).getAddress());
		}

		@Override
		protected void outputResult() {
			Integer resultPosition = getResultPosition();
			LOG.info("Writing value {} to position {}", result, resultPosition);
			opcodes.set(resultPosition, result);
		}
	}
	
	public class UpdateRelativeBaseInstruction extends Instruction {

		protected UpdateRelativeBaseInstruction(OpCode opcode, int index) {
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
		protected Integer calculate() {
			return relativeBase + parameters.get(0).getValue();
		}

		@Override
		protected Integer getResultPosition() {
			return null;
		}

		@Override
		protected void outputResult() {
			relativeBase = result;
		}
	}
	
	public class TerminateInstruction extends Instruction {

		protected TerminateInstruction(OpCode opcode, int index) {
			super(opcode, index);
		}

		@Override
		protected void initialiseParameters() {
			// do nothing
		}

		@Override
		protected Integer calculate() {
			terminated = true;
			return null;
		}

		@Override
		protected Integer getResultPosition() {
			return null;
		}

		@Override
		protected void outputResult() {
			// no output from this instruction
		}
	}
}
