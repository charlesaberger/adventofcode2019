package thebergers.adventofcode2019.jupiter;

import static com.google.common.math.LongMath.gcd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Universe {

	private static final Logger LOG = LoggerFactory.getLogger(Universe.class);
	private final List<Moon> moons;

	public Universe(List<Moon> moons) {
		this.moons = moons;
	}

	public List<Moon> getMoons() {
		return moons;
	}

	public void simulateMotion(Integer timeSteps) {
		Long currentStep = 0L;
		logMoons(currentStep);
		while (currentStep < timeSteps) {
			calculateNewState(Dimension.ALL);
			currentStep++;
			logMoons(currentStep);
		}
	}

	public void calculateNewState(Dimension dimension) {
		GravityCalculator.applyGravity(moons, dimension);
		moons
			.parallelStream()
			.forEach(Moon::applyVelocity);
	}

	public Long simulateAndCompare() {
		Map<Dimension, Long> dimensions = new HashMap<>();
		dimensions.put(Dimension.X_ONLY, 0L);
		dimensions.put(Dimension.Y_ONLY, 0L);
		dimensions.put(Dimension.Z_ONLY, 0L);
		List<Moon> initialState = copyMoons();
		dimensions
			.entrySet()
			.stream()
			.sorted(Map.Entry.comparingByKey())
			.forEach(dimensionEntry -> {
			boolean statesMatch = false;
			Dimension dimension = dimensionEntry.getKey();
			Long steps = dimensionEntry.getValue();
			logMoons("initialState", dimension, steps, initialState);
			do {
				steps++;
				calculateNewState(dimension);
				statesMatch = compareStates(initialState);
				if (LOG.isDebugEnabled()) {
					//logMoons("initialState", dimension, steps, initialState);
					logMoons("currentState", dimension, steps, moons);
					LOG.debug("dimension: {}, steps: {}, statesMatch: {}", dimension, steps, statesMatch);
				}
				if (steps % 5000 == 0) {
					LOG.info("Dimension: {}, Steps: {}", dimension, steps);
				}
			} while (!statesMatch);
			dimensions.put(dimension, steps);
		});
		return dimensions
			.entrySet()
			.stream()
			.map(entry -> {
				Long value = entry.getValue();
				LOG.info("dimension: {}, steps: {}", entry.getKey(), value);
				return value;
			})
			.reduce(1L, (product, value) -> product * (value / gcd(product, value)));
	}

	private boolean compareStates(List<Moon> initialState) {
		for (Moon moon : initialState) {
			Optional<Moon> currentMoon = moons
				.stream()
				.filter(m -> m.getName().equals(moon.getName()))
				.findFirst();
			if (!currentMoon.isPresent()) {
				return false;
			}
			if (!moon.equals(currentMoon.get())) {
				return false;
			}
		}
		return true;
	}

	private List<Moon> copyMoons() {
		return moons
			.parallelStream()
			.map(m -> {
				String name = m.getName();
				Axis x = new Axis(m.getX().getPosition());
				Axis y = new Axis(m.getY().getPosition());
				Axis z = new Axis(m.getZ().getPosition());
				return new Moon(name, x, y, z);
			})
			.collect(Collectors.toList());
	}

	private void logMoons(Long currentStep) {
		LOG.debug("Step {}", currentStep);
		moons
			.stream()
			.forEach(moon -> LOG.debug("{}", moon));
	}

	private void logMoons(String text, Dimension dimension, Long currentStep, List<Moon> moonsToLog) {
		LOG.debug("{}: Dimension: {}, Step {}", text, dimension, currentStep);
		moonsToLog
			.stream()
			.forEach(moon -> LOG.debug("{}", moon));
	}

	public Integer calculateEnergy() {
		return moons
			.stream()
			.mapToInt(Moon::calculateTotalEnergy)
			.sum();
	}
}
