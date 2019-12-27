package thebergers.adventofcode2019.intcodecomputer;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

public class IntcodeComputerRunnerBuilder {
	
	private List<IntcodeComputerBuilder> builders;
	
	public static IntcodeComputerRunnerBuilder newInstance() {
		return new IntcodeComputerRunnerBuilder();
	}
	
	public IntcodeComputerRunnerBuilder addBuilder(IntcodeComputerBuilder builder) {
		if (CollectionUtils.isEmpty(this.builders)) {
			this.builders = new ArrayList<>();
		}
		this.builders.add(builder);
		return this;
	}
	
	public IntcodeComputerRunner build() {
		if (builders.isEmpty()) {
			throw new IllegalArgumentException("No IntcodeComputers specified!");
		}
		IntcodeComputerRunner runner = null;
		if (builders.size() == 1) {
			runner = new SimpleIntcodeComputerRunner(builders.get(0));
		} else {
			runner = new MultiThreadedIntcodeComputerRunner(builders);
		}
		return runner;
	}
}
