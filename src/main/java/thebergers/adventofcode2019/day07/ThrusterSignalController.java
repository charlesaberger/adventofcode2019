package thebergers.adventofcode2019.day07;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThrusterSignalController {

	private static final Logger LOG = LoggerFactory.getLogger(ThrusterSignalController.class);
	
	private Map<String, ThrusterSignalCalculator> calculators;
	
	public Long calculateMaxThrust() {
		Optional<Long> maxThrust = calculators
				.entrySet()
				.stream()
				.map(entry -> {
					try {
						return entry.getValue().calculateThrust();
					} catch (InterruptedException e) {
						LOG.warn("{}", e);
					} catch (ExecutionException e) {
						LOG.warn("{}", e);
					}
					return -1L;
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
}
