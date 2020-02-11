package thebergers.adventofcode2019.hullpainter;

public enum Colour {

	BLACK(0L, "Black"),
	WHITE(1L, "White");
	
	private final Long code;
	
	private final String description;
	
	Colour(Long code, String description) {
		this.code = code;
		this.description = description;
	}
	
	public Long getCode() {
		return code;
	}
	
	public String getDescription() {
		return description;
	}
	
	public static Colour getInstance(Long code) {
		if (code.equals(0L)) {
			return BLACK;
		}
		if (code.equals(1L)) {
			return WHITE;
		}
		return null;
	}
}
