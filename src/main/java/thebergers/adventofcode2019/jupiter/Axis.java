package thebergers.adventofcode2019.jupiter;

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
}
