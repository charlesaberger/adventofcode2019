package thebergers.adventofcode2019.dayone;

import java.util.LinkedList;
import java.util.List;

public class FuelCounterUpper {

	private final List<Module> modules;
	
	public FuelCounterUpper(List<String> input) {
		modules = new LinkedList<>();
		input.forEach(massStr -> {
			Integer mass = Integer.parseInt(massStr);
			modules.add(new Module(mass));
		});
	}

	public int calculateFuel() {
		return modules.stream().mapToInt(Module::calculateFuel).sum();
	}
	
}
