package thebergers.adventofcode2019.intcodecomputer;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public abstract class AbstractIntcodeComputerBuilder<T extends AbstractIntcodeComputerBuilder<T>>
	implements IntcodeComputerBuilderInterface {

	protected Integer sequenceNumber;

	protected String name;

	protected String program;

	protected List<Long> initialInput;

	protected String connectsTo;

	protected abstract T me();

	@Override
	public IntcodeComputerInterface build() {
		if (StringUtils.isEmpty(name)) {
			this.name = "IntcodeComputer";
		}
		if (null == sequenceNumber) {
			sequenceNumber = 1;
		}
		IntcodeComputer computer = new IntcodeComputer(sequenceNumber, name, program);
		if (null != initialInput) {
			initialInput.stream()
				.forEach(value -> computer.addInput(value));
		}
		return computer;
	}

}
