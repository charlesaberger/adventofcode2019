package thebergers.adventofcode2019.intcodecomputer;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

public class IntcodeComputerBuilder {

	private Integer sequenceNumber;
	
	private String name;
	
	private String program;
	
	private List<Integer> initialInput;
	
	private String connectsTo;
	
	public static IntcodeComputerBuilder newInstance() {
		return new IntcodeComputerBuilder();
	}
	
	public IntcodeComputerBuilder setSequenceNumber(Integer sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
		return this;
	}
	
	public IntcodeComputerBuilder setName(String name)  {
		this.name = name;
		return this;
	}
	
	public String getName() {
		return name;
	}
	
	public IntcodeComputerBuilder setProgram(String program) {
		this.program = program;
		return this;
	}
	
	public String getProgram() {
		return program;
	}
	
	public IntcodeComputerBuilder addInput(Integer input) {
		if (CollectionUtils.isEmpty(this.initialInput)) {
			this.initialInput = new LinkedList<>();
		}
		this.initialInput.add(input);
		return this;
	}
	
	public IntcodeComputerBuilder setConnectsTo(String connectsTo) {
		this.connectsTo = connectsTo;
		return this;
	}
	public String getConnectsTo() {
		return connectsTo;
	}
	
	public IntcodeComputer build() {
		IntcodeComputer computer = new IntcodeComputer(sequenceNumber, name, program);
		if (null != initialInput) {
			initialInput.stream()
			.forEach(value -> computer.addInput(value));
		}
		return computer;
	}
}
