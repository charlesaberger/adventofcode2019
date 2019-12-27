package thebergers.adventofcode2019.intcodecomputer;

public class IntcodeComputerResult {

	private final Integer sequenceNumber;
	
	private final String name;
	
	private final String result;
	
	private final Integer output;
	
	private boolean terminated;
	
	public IntcodeComputerResult(Integer sequenceNumber, String name, String result, Integer output, boolean terminated) {
		this.sequenceNumber = sequenceNumber;
		this.name =name; 
		this.result = result;
		this.output = output;
		this.terminated = terminated;
	}
	
	public Integer getSequenceNumber() {
		return sequenceNumber;
	}
	
	public String getName() {
		return name;
	}
	
	public Integer getOutput() {
		return output;
	}
	
	public String getResult() {
		return result;
	}
	public Integer getPosition(int i) {
		return Integer.parseInt(getOpcodes()[i]);
	}

	public boolean isTerminated() {
		return terminated;
	}
	
	public String getNounAndVerb() {
		return getNounAndVerb(1, 2);
	}
	
	public String getNounAndVerb(int nounIndex, int verbIndex) {
		String[] opcodes = getOpcodes();
		return String.format("%d", (Integer.parseInt(opcodes[nounIndex]) * 100) +
				Integer.parseInt(opcodes[verbIndex]));
	}
	
	private String[] getOpcodes() {
		return result.split(",");
	}
	
	public static IntcodeComputerResult errorInstance() {
		return new IntcodeComputerResult(0, "", "", -1, true);
	}
	
	public String toString() {
		return String.format("IntcodeComputerResult: seqNum=%d, name=%s, value = %d, terminated = %s",
				sequenceNumber, name, output, terminated);
	}
}
