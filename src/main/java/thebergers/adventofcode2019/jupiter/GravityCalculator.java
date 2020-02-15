package thebergers.adventofcode2019.jupiter;

import java.util.List;
import java.util.stream.Collectors;

public class GravityCalculator {

	public static void applyGravity(List<Moon> moons) {
		moons
			.parallelStream()
			.forEach(moon -> {
				List<VelocityChange> velocityChanges = moons
					.stream()
					.filter(m -> !m.getName().equals(moon.getName()))
					.map(m -> calculateVelocityChange(m, moon))
					.collect(Collectors.toList());
				moon.applyVelocityChanges(velocityChanges);
			});
	}

	private static VelocityChange calculateVelocityChange(Moon m1, Moon m2) {
		VelocityAdjustment x = getVelocityAdjustment(m1.getPosition().getxPos(), m2.getPosition().getxPos());
		VelocityAdjustment y = getVelocityAdjustment(m1.getPosition().getyPos(), m2.getPosition().getyPos());
		VelocityAdjustment z = getVelocityAdjustment(m1.getPosition().getzPos(), m2.getPosition().getzPos());
		return new VelocityChange(x, y, z);
	}

	private static VelocityAdjustment getVelocityAdjustment(Integer v1, Integer v2) {
		if (v1 < v2) { return VelocityAdjustment.DECREASE; }
		if (v1 > v2) { return VelocityAdjustment.INCREASE; }
		return VelocityAdjustment.NOCHANGE;
	}

	private GravityCalculator() {

	}
}
