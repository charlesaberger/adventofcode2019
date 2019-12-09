package thebergers.adventofcode2019.day02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class IntcodeComputer {

	private final List<Integer> opcodes;
	
	private final InstructionBuilder instructionBuilder;
	
	public IntcodeComputer(String opcodesStr) {
		this.opcodes = initialise(opcodesStr);
		this.instructionBuilder = new InstructionBuilder();
	}

	public void processOpcodes() throws UnknownOpcodeException {
		parseInstructions();
	}

	public void setCode(int index, Integer value) {
		opcodes.set(index, value);
	}

	public Integer getPosition(int i) {
		return opcodes.get(i);
	}

	public String getResult() {
		return opcodes.stream().map(n -> n.toString()).collect(Collectors.joining(","));
	}
	
	public void reset() {
		opcodes.clear();
	}
	
	public void reset(String opcodesStr) {
		reset();
		this.opcodes.addAll(initialise(opcodesStr));
	}
	
	private List<Integer> initialise(String opcodes) {
		String[] values = opcodes.split(",");
		List<String> valuesList = Arrays.asList(values);
		return valuesList.stream().map(Integer::parseInt).collect(Collectors.toList());
	}
	
	public String getNounAndVerb() {
		return String.format("%d", (opcodes.get(1) * 100) + opcodes.get(2));
	}
	
	public String getNounAndVerb(int nounIndex, int verbIndex) {
		return String.format("%d", (opcodes.get(nounIndex) * 100) + opcodes.get(verbIndex));
	}
	
	private void parseInstructions() {
		int instructionPointer = 0;
		while (true) {
			Instruction instruction = instructionBuilder.build(instructionPointer);
			if (instruction.isTerminate()) {
				return;
			}
			instruction.process();
			instructionPointer = instruction.getNextInstructionPointer(instructionPointer);
		}
	}

	enum OpCode {
		ADD(1),
		MULTIPLY(2),
		TERMINATE(99);
		
		private final int value;
		
		public int getValue() {
			return value;
		}

		private OpCode(int value) {
			this.value = value;
		}
		
		public static OpCode getInstance(Integer value) {
			
			switch (value) {
			case 1:
				return ADD;
			case 2:
				return MULTIPLY;
			case 99:
				return TERMINATE;
			default:
				return null;
			}
		}
	}
	
	enum ParameterMode {
		POSITIONAL(0),
		IMMEDIATE(1),
		WRITE(-1);
		
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
			default:
				return WRITE;
			}
		}
	}
	
	public Parameter getParameterInstance(ParameterMode mode, Integer value) {
		switch(mode) {
		case POSITIONAL:
			return new PositionalParameter(value);
		case IMMEDIATE:
			return new ImmediateParameter(value);
		default:
			return new WriteParameter(value);
		}
	}
	
	abstract class Parameter {
		
		private final ParameterMode mode;
		
		protected final Integer value;
		
		private Parameter(ParameterMode mode, Integer value) {
			this.mode = mode;
			this.value = value;
		}
		
		public ParameterMode getMode() {
			return mode;
		}
		
		abstract Integer getValue();

		@Override
		public String toString() {
			return "Parameter [mode=" + mode + ", value=" + value + "]";
		}
	}
	
	class PositionalParameter extends Parameter {
		
		PositionalParameter(Integer value) {
			super(ParameterMode.POSITIONAL, value);
		}

		@Override
		Integer getValue() {
			return opcodes.get(value);
		}
		
	}
	
	class ImmediateParameter extends Parameter {
		
		ImmediateParameter(Integer value) {
			super(ParameterMode.IMMEDIATE, value);
		}

		@Override
		Integer getValue() {
			return value;
		}
		
	}
	
	class WriteParameter extends Parameter {
		
		WriteParameter(Integer value) {
			super(ParameterMode.WRITE, value);
		}

		@Override
		Integer getValue() {
			return value;
		}
	}

	abstract class Instruction {

		protected final int index;
		
		protected final OpCode opcode;
		
		protected List<Parameter> parameters;
		
		protected void process() {
			Integer result = calculate();
			opcodes.set(getResultPosition(), result);
		}
		
		public boolean isTerminate() {
			return opcode.equals(OpCode.TERMINATE);
		}

		protected abstract void initialiseParameters();
		
		protected abstract Integer calculate();
		
		protected abstract Integer getResultPosition();
		
		protected abstract Integer getNextInstructionPointer(int instructionPointer);
		
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

		@Override
		public String toString() {
			return "Instruction [index=" + index + ", opcode=" + opcode + ", parameters=" + parameters + "]";
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
			case TERMINATE:
				return new TerminateInstruction(opcode, index);
			default:
				throw new IllegalArgumentException();
			}
		}
	}

	public class AddInstruction extends Instruction {
		
		private int expectedParameters = 3;

		public AddInstruction(OpCode opcode, int index) {
			super(opcode, index);
		}

		@Override
		protected void initialiseParameters() {
			List<ParameterMode> parameterModes = getParameterModes();
			for (int i = 1; i <= expectedParameters; i++) {
				if (i > parameterModes.size()) {
					parameterModes.add(i <= 2 ? ParameterMode.POSITIONAL : ParameterMode.WRITE);
				}
			}
			int paramIndex = index;
			parameters = new ArrayList<>();
			for (ParameterMode mode : parameterModes) {
				paramIndex++;
				Integer value = opcodes.get(paramIndex);
				parameters.add(getParameterInstance(mode, value));
			}
		}
		
		@Override
		protected Integer calculate() {
			Integer noun = parameters.get(0).getValue();
			Integer verb = parameters.get(1).getValue();
			return noun + verb;
		}

		@Override
		protected Integer getNextInstructionPointer(int instructionPointer) {
			return instructionPointer + 4;
		}

		@Override
		protected Integer getResultPosition() {
			return parameters.get(2).getValue();
		}

	}

	public class MultiplyInstruction extends Instruction {
		
		private int expectedParameters = 3;

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
		protected Integer getNextInstructionPointer(int instructionPointer) {
			return instructionPointer + 4;
		}

		@Override
		protected void initialiseParameters() {
			List<ParameterMode> parameterModes = getParameterModes();
			for (int i = 1; i <= expectedParameters; i++) {
				if (i > parameterModes.size()) {
					parameterModes.add(i <= 2 ? ParameterMode.POSITIONAL : ParameterMode.WRITE);
				}
			}
			int paramIndex = index;
			parameters = new ArrayList<>();
			for (ParameterMode mode : parameterModes) {
				paramIndex++;
				Integer value = opcodes.get(paramIndex);
				parameters.add(getParameterInstance(mode, value));
			}
		}

		@Override
		protected Integer getResultPosition() {
			return parameters.get(2).getValue();
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
			return null;
		}

		@Override
		protected Integer getResultPosition() {
			return null;
		}

		@Override
		protected Integer getNextInstructionPointer(int instructionPointer) {
			return null;
		}
	}
}
