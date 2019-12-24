package thebergers.adventofcode2019.intcodecomputer;

import java.util.List;
import java.util.stream.Collectors;

public class IntcodeComputerResult {

	private final String result;
	
	private final Integer output;
	
	private boolean terminated;
	
	public IntcodeComputerResult(String result, Integer output, boolean terminated) {
		this.result = result;
		this.output = output;
		this.terminated = terminated;
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
}
