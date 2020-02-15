package thebergers.adventofcode2019.jupiter;

public class VelocityChange {

	private final VelocityAdjustment x;

	private final VelocityAdjustment y;

	private final VelocityAdjustment z;

	public VelocityChange(VelocityAdjustment x, VelocityAdjustment y, VelocityAdjustment z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public VelocityAdjustment getX() {
		return x;
	}

	public VelocityAdjustment getY() {
		return y;
	}

	public VelocityAdjustment getZ() {
		return z;
	}
}
