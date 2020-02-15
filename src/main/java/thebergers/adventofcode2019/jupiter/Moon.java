package thebergers.adventofcode2019.jupiter;

import java.util.List;
import java.util.Objects;

public class Moon {

	private final String name;

	private Axis x;

	private Axis y;

	private Axis z;

	public Moon(String name, Axis x, Axis y, Axis z) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public String getName() {
		return name;
	}

	public Axis getX() {
		return x;
	}

	public Axis getY() {
		return y;
	}

	public Axis getZ() {
		return z;
	}

	public void applyVelocityChanges(List<VelocityChange> velocityChanges) {
		velocityChanges
			.stream()
			.forEach(vc -> {
				x.applyGravity(vc.getX());
				y.applyGravity(vc.getY());
				z.applyGravity(vc.getZ());
			});
	}

	public void applyVelocity() {
		x.applyVelocity();
		y.applyVelocity();
		z.applyVelocity();
	}
	
	public Integer calculateTotalEnergy() {
		return calculatePotentialEnergy() * calculateKineticEnergy();
	}

	private Integer calculatePotentialEnergy() {
		return Math.abs(x.getPosition()) + Math.abs(y.getPosition()) +
			Math.abs(z.getPosition());
	}

	private Integer calculateKineticEnergy() {
		return Math.abs(x.getVelocity()) + Math.abs(y.getVelocity()) +
			Math.abs(z.getVelocity());
	}

	@Override
	public String toString() {
		return String.format("%s: pos=<x=%d, y=%d, z=%d>, vel=<x=%d, y=%d, z=%d>",
			name, x.getPosition(), y.getPosition(), z.getPosition(), x.getVelocity(),
			y.getVelocity(), z.getVelocity());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Moon moon = (Moon) o;
		return name.equals(moon.name) &&
			x.equals(moon.x) &&
			y.equals(moon.y) &&
			z.equals(moon.z);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, x, y, z);
	}
}
