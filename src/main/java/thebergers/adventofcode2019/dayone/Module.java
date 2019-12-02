package thebergers.adventofcode2019.dayone;

public class Module {

	private final int mass;
	
	public Module(int mass) {
		this.mass = mass;
	}

	public int calculateFuel() {
		return Math.floorDiv(mass, 3) - 2;
	}
	
	public int getMass() {
		return mass;
	}

	@Override
	public String toString() {
		return "Module [mass=" + mass + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + mass;
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
		Module other = (Module) obj;
		if (mass != other.mass)
			return false;
		return true;
	}
}
