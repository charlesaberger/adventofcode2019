package thebergers.adventofcode2019.day07;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class ThrusterSignalController {

	private Map<String, ThrusterSignalCalculator> calculators;
	
	public Integer calculateMaxThrust() {
		Optional<Integer> maxThrust = calculators
				.entrySet()
				.stream()
				.map(entry -> {
					try {
						return entry.getValue().calculateThrust();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (ExecutionException e) {
						e.printStackTrace();
					}
					return -1;
				})
				.sorted(Comparator.reverseOrder())
				.findFirst();
		if (maxThrust.isPresent()) {
			return maxThrust.get();
		}
		throw new RuntimeException("Unable to determine max thrust!");
	}

	public void addThrusterSignalCalculator(ThrusterSignalCalculator calculator ) {
		if (null == calculators) {
			calculators = new HashMap<>();
		}
		calculators.put(calculator.getPhaseSetting(), calculator);
	}

	public Integer calculateMaxThrustWithFeedback() {
		Optional<Integer> maxThrust = calculators
				.entrySet()
				.stream()
				//.parallelStream()
				.map(entry -> entry.getValue().calculateThrustWithFeedback(0))
				.sorted(Comparator.reverseOrder())
				.findFirst();
		if (maxThrust.isPresent()) {
			return maxThrust.get();
		}
		throw new RuntimeException("Unable to determine max thrust!");
	}
}
