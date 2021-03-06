package thebergers.adventofcode2019.monitoringstation;

public class Asteroid {

	private final Integer x;
	
	private final Integer y;
	
	private Long visibleAsteroids = 0L;
	
	public Asteroid (Integer x, Integer y) {
		this.x = x;
		this.y = y;
	}
	
	public Asteroid(String coordinates, Long visibleAsteroids) {
		String[] coords = coordinates.split(",");
		this.x = Integer.parseInt(coords[0]);
		this.y = Integer.parseInt(coords[1]);
		this.visibleAsteroids = visibleAsteroids;
	}
	
	public Integer getX() {
		return x;
	}
	
	public Integer getY() {
		return y;
	}
	
	public void addVisibleAsteroid() {
		visibleAsteroids++;
	}

	public void setVisibleAsteroids(Long visibleAsteroids) {
		this.visibleAsteroids = visibleAsteroids;
	}

	public Long getVisibleAsteroids() {
		return visibleAsteroids;
	}

	public Integer getCoordinates() {
		return (x * 100) + y;
	}

	public Double distanceFrom(Asteroid asteroid) {
		Integer xDiff = asteroid.getX() - this.getX();
		Integer yDiff = asteroid.getY() - this.getY();
		return Math.sqrt((xDiff * xDiff) + (yDiff * yDiff));
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
		Asteroid other = (Asteroid) obj;
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
		return "Asteroid [x=" + x + ", y=" + y + "]";
	}
}
