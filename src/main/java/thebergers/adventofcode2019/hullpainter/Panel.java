package thebergers.adventofcode2019.hullpainter;

public class Panel {

	private final Integer x;
	
	private final Integer y;
	
	private Colour colour;
	
	private boolean painted = false;
	
	public Panel(Integer x, Integer y) {
		this.x = x;
		this.y = y;
		this.colour = Colour.BLACK;
	}

	public Colour getColour() {
		return colour;
	}

	public void setColour(Colour colour) {
		this.colour = colour;
	}

	public boolean isPainted() {
		return painted;
	}

	public void setPainted(boolean painted) {
		this.painted = painted;
	}

	public Integer getX() {
		return x;
	}

	public Integer getY() {
		return y;
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
		Panel other = (Panel) obj;
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
		return "Panel [x=" + x + ", y=" + y + ", colour=" + colour + ", painted=" + painted + "]";
	}

	public String visualise() {
		return colour.equals(Colour.BLACK) ? "." : "#";
	}

}
