package thebergers.adventofcode2019.day02;

public class UnknownOpcodeException extends Exception {

	private final int index;
	
	private final Integer opcode;
	
	public UnknownOpcodeException(int index, Integer opcode) {
		this.index = index;
		this.opcode = opcode;
	}

	@Override
	public String getMessage() {
		return String.format("Unknown opcode %s at index %d.", opcode, index);
	}
	
}
