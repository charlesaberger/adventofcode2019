package thebergers.adventofcode2019.universalorbitmap;

import java.util.LinkedList;
import java.util.List;

public class Planet {

	private final String name;
	
	private Planet orbits;
	
	private final List<Planet> orbitedBy;
	
	public Planet(String name) {
		this.name = name;
		this.orbitedBy = new LinkedList<>();
	}

	public Planet getOrbits() {
		return orbits;
	}

	public void setOrbits(Planet orbits) {
		this.orbits = orbits;
	}

	public String getName() {
		return name;
	}

	public List<Planet> getOrbitedBy() {
		return orbitedBy;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Planet other = (Planet) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Planet [");
		if (name != null)
			builder.append("name=").append(name).append(", ");
		if (orbits != null)
			builder.append("orbits=").append(orbits).append(", ");
		if (orbitedBy != null)
			builder.append("orbitedBy=").append(orbitedBy);
		builder.append("]");
		return builder.toString();
	}

	public void addToOrbits(Planet orbiting) {
		orbitedBy.add(orbiting);
	}
	
	
}
