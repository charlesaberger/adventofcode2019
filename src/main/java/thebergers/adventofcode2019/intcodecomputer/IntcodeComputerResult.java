package thebergers.adventofcode2019.intcodecomputer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class IntcodeComputerResult {

	private final Integer sequenceNumber;
	
	private final String name;
	
	private final String result;
	
	private final List<Long> output;
	
	private boolean terminated;
	
	public IntcodeComputerResult(Integer sequenceNumber, String name, String result, List<Long> output, boolean terminated) {
		this.sequenceNumber = sequenceNumber;
		this.name = name;
		this.result = result;
		this.output = output;
		this.terminated = terminated;
	}

	public IntcodeComputerResult(Integer sequenceNumber, String name, String result, boolean terminated) {
		this.sequenceNumber = sequenceNumber;
		this.name = name;
		this.result = result;
		this.output = parseResult(result);
		this.terminated = terminated;
	}

	private List<Long> parseResult(String resultStr) {
		return Arrays.asList(resultStr.split(","))
			.stream()
			.map(item -> Long.parseLong(item))
			.collect(Collectors.toList());
	}

	public Integer getSequenceNumber() {
		return sequenceNumber;
	}
	
	public String getName() {
		return name;
	}
	
	public Long getOutput() {
		return output.get(0);
	}

	public List<Long> getOutputAsList() { return output; }
	
	public String getAllOutput() {
		return output.stream().map(val -> Long.toString(val)).collect(Collectors.joining(","));
	}
	
	public String getResult() {
		return result;
	}
	public Long getPosition(int i) {
		return Long.parseLong(getOpcodes()[i]);
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
		return new IntcodeComputerResult(0, "", "", Collections.emptyList(), true);
	}
	
	public String toString() {
		return String.format("IntcodeComputerResult: seqNum=%d, name=%s, output=%s, terminated=%s",
				sequenceNumber, name, output, terminated);
	}
}
