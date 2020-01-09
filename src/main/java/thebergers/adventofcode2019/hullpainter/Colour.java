package thebergers.adventofcode2019.hullpainter;

public enum Colour {

	BLACK(0, "Black"),
	WHITE(1, "White");
	
	private final Integer code;
	
	private final String description;
	
	private Colour(Integer code, String description) {
		this.code = code;
		this.description = description;
	}
	
	public Integer getCode() {
		return code;
	}
	
	public String getDescription() {
		return description;
	}
	
	public static Colour getInstance(Integer code) {
		switch (code) {
		case 0:
			return BLACK;
		case 1:
			return WHITE;
		default:
			return null;
		}
	}
}
