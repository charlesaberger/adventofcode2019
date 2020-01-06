package thebergers.adventofcode2019.monitoringstation;

import java.util.List;

public class AsteroidBeltBuilder {
	
	private static final String ASTEROID = "#";

	private List<String> mapData;
	
	public static AsteroidBeltBuilder newInstance() {
		return new AsteroidBeltBuilder();
	}
	
	public AsteroidBeltBuilder setMapData(List<String> mapData) {
		this.mapData = mapData;
		return this;
	}
	
	public AsteroidBelt build() {
		AsteroidBelt asteroidBelt = new AsteroidBelt();
		for (Integer y = 0; y < mapData.size(); y++) {
			parseMapLine(y, mapData.get(y), asteroidBelt);
		}
		return asteroidBelt;
	}
	
	private void parseMapLine(Integer y, String mapLine, AsteroidBelt asteroidBelt) {
		for (Integer x = 0; x < mapLine.length(); x++) {
			String character = mapLine.substring(x, x + 1);
			if (character.equals(ASTEROID)) {
				asteroidBelt.addAsteroid(new Asteroid(x, y));
			}
		}
	}
}
