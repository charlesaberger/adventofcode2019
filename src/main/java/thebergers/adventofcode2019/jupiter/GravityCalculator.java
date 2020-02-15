package thebergers.adventofcode2019.jupiter;

import java.util.List;
import java.util.stream.Collectors;

public class GravityCalculator {

	public static void applyGravity(List<Moon> moons, Dimension dimension) {
		moons
			.parallelStream()
			.forEach(moon -> {
				List<VelocityChange> velocityChanges = moons
					.parallelStream()
					.filter(m -> !m.getName().equals(moon.getName()))
					.map(m -> calculateVelocityChange(m, moon, dimension))
					.collect(Collectors.toList());
				moon.applyVelocityChanges(velocityChanges);
			});
	}

	private static VelocityChange calculateVelocityChange(Moon m1, Moon m2, Dimension dimension) {
		VelocityAdjustment x = VelocityAdjustment.NOCHANGE;
		VelocityAdjustment y = VelocityAdjustment.NOCHANGE;
		VelocityAdjustment z = VelocityAdjustment.NOCHANGE;
		switch (dimension) {
			case X_ONLY:
				x = getVelocityAdjustment(m1.getX().getPosition(), m2.getX().getPosition());
				y = VelocityAdjustment.NOCHANGE;
				z = VelocityAdjustment.NOCHANGE;
				break;
			case Y_ONLY:
				x = VelocityAdjustment.NOCHANGE;
				y = getVelocityAdjustment(m1.getY().getPosition(), m2.getY().getPosition());
				z = VelocityAdjustment.NOCHANGE;
				break;
			case Z_ONLY:
				x = VelocityAdjustment.NOCHANGE;
				y = VelocityAdjustment.NOCHANGE;
				z = getVelocityAdjustment(m1.getZ().getPosition(), m2.getZ().getPosition());
				break;
			case ALL:
				x = getVelocityAdjustment(m1.getX().getPosition(), m2.getX().getPosition());
				y = getVelocityAdjustment(m1.getY().getPosition(), m2.getY().getPosition());
				z = getVelocityAdjustment(m1.getZ().getPosition(), m2.getZ().getPosition());
				break;
		}
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
