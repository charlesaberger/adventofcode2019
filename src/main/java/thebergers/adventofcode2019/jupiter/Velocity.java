package thebergers.adventofcode2019.jupiter;

import java.util.Objects;

public class Velocity {

	private int x;

	private int y;

	private int z;

	public Velocity(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	public void adjustX(VelocityAdjustment va) {
		switch (va) {
			case DECREASE:
				x--;
				break;
			case INCREASE:
				x++;
				break;
			case NOCHANGE:
				break;
		}
	}

	public void adjustY(VelocityAdjustment va) {
		switch (va) {
			case DECREASE:
				y--;
				break;
			case INCREASE:
				y++;
				break;
			case NOCHANGE:
				break;
		}
	}

	public void adjustZ(VelocityAdjustment va) {
		switch (va) {
			case DECREASE:
				z--;
				break;
			case INCREASE:
				z++;
				break;
			case NOCHANGE:
				break;
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Velocity velocity = (Velocity) o;
		return x == velocity.x &&
			y == velocity.y &&
			z == velocity.z;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y, z);
	}

	@Override
	public String toString() {
		return String.format("vel=<x=%d, y=%d, z=%d>", x, y, z);
	}

	public Integer calculateKineticEnergy() {
		return Math.abs(x) + Math.abs(y) + Math.abs(z);
	}
}
