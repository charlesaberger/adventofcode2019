package thebergers.adventofcode2019.jupiter;

import java.util.Objects;

public class Position {

	private int xPos;

	private int yPos;

	private int zPos;

	public Position(int xPos, int yPos, int zPos) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.zPos = zPos;
	}

	public int getxPos() {
		return xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public int getzPos() {
		return zPos;
	}

	public void applyXVelocity(Integer velocity) {
		xPos += velocity;
	}

	public void applyYVelocity(Integer velocity) {
		yPos += velocity;
	}

	public void applyZVelocity(Integer velocity) {
		zPos += velocity;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Position position = (Position) o;
		return xPos == position.xPos &&
			yPos == position.yPos &&
			zPos == position.zPos;
	}

	@Override
	public int hashCode() {
		return Objects.hash(xPos, yPos, zPos);
	}

	@Override
	public String toString() {
		return String.format("pos=<x=%d, y=%d, z=%d>", xPos, yPos, zPos);
	}

	public Integer calculatePotentialEnergy() {
		return Math.abs(xPos) + Math.abs(yPos) + Math.abs(zPos);
	}
}
