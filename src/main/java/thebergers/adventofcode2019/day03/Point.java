package thebergers.adventofcode2019.day03;

import java.util.UUID;

public class Point {

	private final UUID wireId;
	
	private final Integer x;
	
	private final Integer y;
	
	public Point(UUID wireId, Integer x, Integer y) {
		this.wireId = wireId;
		this.x = x;
		this.y = y;
	}
	
	public UUID getWireId() {
		return wireId;
	}

	public Integer getX() {
		return x;
	}

	public Integer getY() {
		return y;
	}
	
	public Integer distanceFromOrigin() {
		return (x >= 0 ? x : x * -1) + (y >=0 ? y : y * -1);
	}

	public boolean isOrigin() {
		return x.equals(0) && y.equals(0);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((x == null) ? 0 : x.hashCode());
		result = prime * result + ((y == null) ? 0 : y.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (x == null) {
			if (other.x != null)
				return false;
		} else if (!x.equals(other.x))
			return false;
		if (y == null) {
			if (other.y != null)
				return false;
		} else if (!y.equals(other.y))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + "]";
	}
}
