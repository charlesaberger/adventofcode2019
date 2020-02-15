package thebergers.adventofcode2019.jupiter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
			calculateNewState();
			currentStep++;
			logMoons(currentStep);
		}
	}

	public void calculateNewState() {
		GravityCalculator.applyGravity(moons);
		moons
			.parallelStream()
			.forEach(Moon::applyVelocity);
	}

	public Long simulateAndCompare() {
		Long steps = 0L;
		List<Moon> initialState = copyMoons();
		logMoons("initialState", steps, initialState);
		boolean statesMatch = false;
		do {
			steps++;
			calculateNewState();
			statesMatch = compareStates(initialState);
			if (LOG.isDebugEnabled()) {
				logMoons("initialState", steps, initialState);
				logMoons("currentState", steps, moons);
				LOG.debug("steps: {}, statesMatch: {}", steps, statesMatch);
			}
			if (steps % 5000 == 0) {
				LOG.info("Steps: {}", steps);
			}
		} while(!statesMatch);
		return steps;
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
				Position newPos = new Position(m.getPosition().getxPos(), m.getPosition().getyPos(),
					m.getPosition().getzPos());
				return new Moon(name, newPos, UniverseHelper.initialVelocity());
			})
			.collect(Collectors.toList());
	}

	private void logMoons(Long currentStep) {
		LOG.debug("Step {}", currentStep);
		moons
			.stream()
			.forEach(moon -> LOG.debug("{}", moon));
	}

	private void logMoons(String text, Long currentStep, List<Moon> moonsToLog) {
		LOG.debug("{}: Step {}", text, currentStep);
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
