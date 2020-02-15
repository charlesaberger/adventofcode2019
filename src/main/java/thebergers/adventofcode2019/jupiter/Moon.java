package thebergers.adventofcode2019.jupiter;

import java.util.List;

public class Moon {

	private final String name;

	private final Position position;

	private final Velocity velocity;

	public Moon(String name, Position position, Velocity velocity) {
		this.name = name;
		this.position = position;
		this.velocity = velocity;
	}

	public String getName() {
		return name;
	}

	public Position getPosition() {
		return position;
	}

	public Velocity getVelocity() {
		return velocity;
	}

	public void applyVelocityChanges(List<VelocityChange> velocityChanges) {
		velocityChanges
			.stream()
			.forEach(vc -> {
				velocity.adjustX(vc.getX());
				velocity.adjustY(vc.getY());
				velocity.adjustZ(vc.getZ());
			});
	}

	public void applyVelocity() {
		position.applyXVelocity(velocity.getX());
		position.applyYVelocity(velocity.getY());
		position.applyZVelocity(velocity.getZ());
	}
	
	public Integer calculateTotalEnergy() {
		return position.calculatePotentialEnergy() * velocity.calculateKineticEnergy();
	}

	@Override
	public String toString() {
		return String.format("%s: %s, %s, energy=%d", name, position, velocity, calculateTotalEnergy());
	}
}
