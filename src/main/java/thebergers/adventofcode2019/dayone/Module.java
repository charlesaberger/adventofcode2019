package thebergers.adventofcode2019.dayone;

public class Module {

	private final int mass;
	
	public Module(int mass) {
		this.mass = mass;
	}

	public int calculateFuel() {
		return Math.floorDiv(mass, 3) - 2;
	}
	
	
}
