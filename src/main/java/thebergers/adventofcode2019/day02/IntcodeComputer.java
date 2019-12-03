package thebergers.adventofcode2019.day02;

import java.util.Arrays;
import java.util.List;
import java.util.function.IntPredicate;
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
	
	private void parseInstructions() throws UnknownOpcodeException {
		int index = 0;
		while (true) {
			OpCode opcode = getNextOpcode(index);
			if (null == opcode) {
				throw new UnknownOpcodeException(index, opcodes.get(index));
			}
			if (opcode.equals(OpCode.TERMINATE)) {
				return;
			}
			OpcodeOperation operation = getOperation(opcode, index);
			operation.process();
			index = getNextIndex(index);
		}
	}
	
	private int getNextIndex(int index) {
		return index + 4;
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

	abstract class OpcodeOperation {

		protected final Integer operand1;
		
		protected final Integer operand2;
		
		protected final Integer resultPosition;
		
		protected void process() {
			Integer result = calculate();
			opcodes.set(resultPosition, result);
		};
		
		protected abstract Integer calculate();
		
		
		protected OpcodeOperation(int index) {
			int operand1Index = opcodes.get(index + 1);
			this.operand1 = opcodes.get(operand1Index);
			int operand2Index = opcodes.get(index + 2);
			this.operand2 = opcodes.get(operand2Index);
			this.resultPosition = opcodes.get(index + 3);
		}
		
	}
	
	OpcodeOperation getOperation(OpCode opcode, int index) throws UnknownOpcodeException {
		switch (opcode) {
		case ADD:
			return new AddOperation(index);
		case MULTIPLY:
			return new MultiplyOperation(index);
		default:
			throw new UnknownOpcodeException(index, opcodes.get(index));
		}
	}

	public class AddOperation extends OpcodeOperation {

		public AddOperation(int index) {
			super(index);
		}
		
		@Override
		protected Integer calculate() {
			return operand1 + operand2;
		}

	}

	public class MultiplyOperation extends OpcodeOperation {

		public MultiplyOperation(int index) {
			super(index);
		}
		
		@Override
		protected Integer calculate() {
			return operand1 * operand2;
		}

	}

	private OpCode getNextOpcode(int index) {
		return OpCode.getInstance(this.opcodes.get(index));
	}
}
