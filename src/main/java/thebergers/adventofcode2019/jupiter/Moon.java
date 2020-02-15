package thebergers.adventofcode2019.jupiter;

import java.util.List;

public class Moon {

	private final String name;

	private Axis x;

	private Axis y;

	private Axis z;

	/*private final Position position;

	private final Velocity velocity;

	public Moon(String name, Position position, Velocity velocity) {
		this.name = name;
		this.position = position;
		this.velocity = velocity;
	}*/

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

	/*public Position getPosition() {
		return position;
	}

	public Velocity getVelocity() {
		return velocity;
	}*/

	/*public void applyVelocityChanges(List<VelocityChange> velocityChanges) {
		velocityChanges
			.stream()
			.forEach(vc -> {
				velocity.adjustX(vc.getX());
				velocity.adjustY(vc.getY());
				velocity.adjustZ(vc.getZ());
			});
	}*/
	public void applyVelocityChanges(List<VelocityChange> velocityChanges) {
		velocityChanges
			.stream()
			.forEach(vc -> {
				x.applyGravity(vc.getX());
				y.applyGravity(vc.getY());
				z.applyGravity(vc.getZ());
			});
	}

	/*public void applyVelocity() {
		position.applyXVelocity(velocity.getX());
		position.applyYVelocity(velocity.getY());
		position.applyZVelocity(velocity.getZ());
	}*/
	public void applyVelocity() {
		x.applyVelocity();
		y.applyVelocity();
		z.applyVelocity();
	}
	
	/*public Integer calculateTotalEnergy() {
		return position.calculatePotentialEnergy() * velocity.calculateKineticEnergy();
	}*/
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

	/*@Override
	public String toString() {
		return String.format("%s: %s, %s, energy=%d", name, position, velocity, calculateTotalEnergy());
	}*/

	@Override
	public String toString() {
		return String.format("%s: pos=<x=%d, y=%d, z=%d>, vel=<x=%d, y=%d, z=%d>",
			name, x.getPosition(), y.getPosition(), z.getPosition(), x.getVelocity(),
			y.getVelocity(), z.getVelocity());
	}
}
