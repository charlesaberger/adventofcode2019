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
		return build(false);
	}

	public IntcodeComputerRunner build(boolean observeChanges) {
		if (builders.isEmpty()) {
			throw new IllegalArgumentException("No IntcodeComputers specified!");
		}
		IntcodeComputerRunner runner = null;
		if (observeChanges) {
			return new MultiThreadedIntcodeComputerRunner(builders);
		}
		if (builders.size() == 1) {
			return new SimpleIntcodeComputerRunner(builders.get(0));
		}
		return new MultiThreadedIntcodeComputerRunner(builders);
	}
}
