package thebergers.adventofcode2019.intcodecomputer;

import java.util.List;
import java.util.stream.Collectors;

public class IntcodeComputerResult {

	//private final List<Integer> opcodes;
	private final String result;
	
	private final Integer output;
	
	private boolean terminated;
	
	public IntcodeComputerResult(/*List<Integer> opcodes*/String result, Integer output, boolean terminated) {
		//this.opcodes = opcodes;
		this.result = result;
		this.output = output;
		this.terminated = terminated;
	}
	
	public Integer getOutput() {
		return output;
	}
	
	public String getResult() {
		return result; //opcodes.stream().map(n -> n.toString()).collect(Collectors.joining(","));
	}

	public boolean isTerminated() {
		return terminated;
	}
	
	public String getNounAndVerb() {
		return getNounAndVerb(1, 2); //String.format("%d", (opcodes.get(1) * 100) + opcodes.get(2));
	}
	
	public String getNounAndVerb(int nounIndex, int verbIndex) {
		String[] opcodes = result.split(",");
		return String.format("%d", (Integer.parseInt(opcodes[nounIndex]) * 100) +
				Integer.parseInt(opcodes[verbIndex]));
	}
}
