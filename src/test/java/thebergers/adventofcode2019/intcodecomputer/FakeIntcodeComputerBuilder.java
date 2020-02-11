package thebergers.adventofcode2019.intcodecomputer;

import org.apache.commons.collections4.CollectionUtils;

import java.util.LinkedList;
import java.util.List;

public class FakeIntcodeComputerBuilder extends AbstractIntcodeComputerBuilder<FakeIntcodeComputerBuilder> {

	private List<IntcodeComputerResult> testResults;

	public static FakeIntcodeComputerBuilder newInstance() {
		return new FakeIntcodeComputerBuilder();
	}

	@Override
	public FakeIntcodeComputerBuilder setSequenceNumber(Integer sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
		return me();
	}

	@Override
	public FakeIntcodeComputerBuilder setName(String name)  {
		this.name = name;
		return me();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public FakeIntcodeComputerBuilder setProgram(String program) {
		this.program = program;
		return me();
	}

	@Override
	public String getProgram() {
		return program;
	}

	@Override
	public FakeIntcodeComputerBuilder addInput(Long input) {
		if (CollectionUtils.isEmpty(this.initialInput)) {
			this.initialInput = new LinkedList<>();
		}
		this.initialInput.add(input);
		return me();
	}

	@Override
	public FakeIntcodeComputerBuilder setConnectsTo(String connectsTo) {
		this.connectsTo = connectsTo;
		return me();
	}
	@Override
	public String getConnectsTo() {
		return connectsTo;
	}

	@Override
	public IntcodeComputerInterface build() {
		FakeIntcodeComputer computer = new FakeIntcodeComputer(name, testResults);
		return (IntcodeComputerInterface)computer;
	}

	public FakeIntcodeComputerBuilder setTestResults(List<IntcodeComputerResult> testResults) {
		this.testResults = testResults;
		return this;
	}

	@Override
	protected FakeIntcodeComputerBuilder me() {
		return this;
	}
}
