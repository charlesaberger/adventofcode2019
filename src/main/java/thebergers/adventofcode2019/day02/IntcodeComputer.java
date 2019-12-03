package thebergers.adventofcode2019.day02;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class IntcodeComputer {

	private final List<Integer> opcodes;
	
	public IntcodeComputer(String opcodes) {
		String[] values = opcodes.split(",");
		List<String> valuesList = Arrays.asList(values);
		this.opcodes = valuesList.stream().map(Integer::parseInt).collect(Collectors.toList());
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
	
	public String getNounAndVerb() {
		return String.format("%d", (opcodes.get(1) * 100) + opcodes.get(2));
	}
	
	private void parseInstructions() throws UnknownOpcodeException {
		int instructionPointer = 0;
		while (true) {
			OpCode opcode = getNextOpcode(instructionPointer);
			if (null == opcode) {
				throw new UnknownOpcodeException(instructionPointer, opcodes.get(instructionPointer));
			}
			if (opcode.equals(OpCode.TERMINATE)) {
				return;
			}
			Instruction instruction = getInstruction(opcode, instructionPointer);
			instruction.process();
			instructionPointer = getNextInstructionPointer(instructionPointer);
		}
	}
	
	private int getNextInstructionPointer(int instructionPointer) {
		return instructionPointer + 4;
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

	abstract class Instruction {

		protected final Integer noun;
		
		protected final Integer verb;
		
		protected final Integer resultPosition;
		
		protected void process() {
			Integer result = calculate();
			opcodes.set(resultPosition, result);
		};
		
		protected abstract Integer calculate();
		
		
		protected Instruction(int index) {
			int operand1Index = opcodes.get(index + 1);
			this.noun = opcodes.get(operand1Index);
			int operand2Index = opcodes.get(index + 2);
			this.verb = opcodes.get(operand2Index);
			this.resultPosition = opcodes.get(index + 3);
		}
		
	}
	
	Instruction getInstruction(OpCode opcode, int index) throws UnknownOpcodeException {
		switch (opcode) {
		case ADD:
			return new AddInstruction(index);
		case MULTIPLY:
			return new MultiplyInstruction(index);
		default:
			throw new UnknownOpcodeException(index, opcodes.get(index));
		}
	}

	public class AddInstruction extends Instruction {

		public AddInstruction(int index) {
			super(index);
		}
		
		@Override
		protected Integer calculate() {
			return noun + verb;
		}

	}

	public class MultiplyInstruction extends Instruction {

		public MultiplyInstruction(int index) {
			super(index);
		}
		
		@Override
		protected Integer calculate() {
			return noun * verb;
		}

	}

	private OpCode getNextOpcode(int index) {
		return OpCode.getInstance(this.opcodes.get(index));
	}
}
