package thebergers.adventofcode2019.imageprocessor;

public enum Pixel {

	BLACK(0, true),
	WHITE(1, true),
	TRANSPARENT(2, false);
	
	private Pixel(Integer id, boolean visible) {
		this.id = id;
		this.visible = visible;
	}
	
	private boolean visible;
	
	private Integer id;
	
	public Integer getId() {
		return id;
	}
	
	public boolean isVisible() {
		return visible;
	}

	public static Pixel fromInteger(Integer pixel) {
		if (pixel.equals(Pixel.BLACK.id)) {
			return BLACK;
		}
		if (pixel.equals(TRANSPARENT.id)) {
			return TRANSPARENT;
		}
		if (pixel.equals(WHITE.id)) {
			return WHITE;
		}
		return null;
	}
}
