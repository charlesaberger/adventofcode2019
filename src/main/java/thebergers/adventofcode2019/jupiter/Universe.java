package thebergers.adventofcode2019.jupiter;

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
		Integer currentStep = 0;
		logMoons(currentStep);
		while (currentStep < timeSteps) {
			GravityCalculator.applyGravity(moons);
			moons
				.parallelStream()
				.forEach(Moon::applyVelocity);
			currentStep++;
			logMoons(currentStep);
		}
	}

	private void logMoons(int currentStep) {
		LOG.debug("Step {}", currentStep);
		moons
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
