package thebergers.adventofcode2019.dayone;

import java.util.LinkedList;
import java.util.List;
import java.util.function.IntPredicate;

public class FuelCounterUpper {

	private final List<Module> modules;
	
	public FuelCounterUpper(List<String> input) {
		modules = new LinkedList<>();
		input.forEach(massStr -> {
			Integer mass = Integer.parseInt(massStr);
			Module module = new Module(mass);
			modules.addAll(calculateAdditionalFuel(module));
		});
	}

	public int calculateFuel() {
		IntPredicate greaterThanZero = (x) -> x > 0;
		return modules.stream()
				.mapToInt(Module::calculateFuel)
				.filter(greaterThanZero)
				.sum();
	}

	private List<Module> calculateAdditionalFuel(Module firstModule) {
		LinkedList<Module> extraModules = new LinkedList<>();
		Module next = firstModule;
		int fuel = 0;
		do {
			fuel = next.calculateFuel();
			extraModules.add(next);
			next = new Module(next.calculateFuel());
		}
		while (fuel > 0);
		return extraModules;
	}
	
	public List<Module> getModules() {
		return modules;
	}
	
}
