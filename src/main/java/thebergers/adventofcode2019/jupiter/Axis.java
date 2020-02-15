package thebergers.adventofcode2019.jupiter;

import java.util.Objects;

public class Axis {

	private Integer position;

	private Integer velocity;

	public Axis(Integer position) {
		this.position = position;
		this.velocity = 0;
	}

	public Integer getPosition() {
		return position;
	}

	public Integer getVelocity() {
		return velocity;
	}

	public void applyGravity(VelocityAdjustment va) {
		switch (va) {
			case DECREASE:
				velocity--;
				break;
			case INCREASE:
				velocity++;
				break;
			case NOCHANGE:
				break;
		}
	}

	public void applyVelocity() {
		position += velocity;
	}

	@Override
	public String toString() {
		return "Axis{" +
			"position=" + position +
			", velocity=" + velocity +
			'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Axis axis = (Axis) o;
		return position.equals(axis.position) &&
			velocity.equals(axis.velocity);
	}

	@Override
	public int hashCode() {
		return Objects.hash(position, velocity);
	}
}
