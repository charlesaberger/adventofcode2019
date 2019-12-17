package thebergers.adventofcode2019.universalorbitmap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class UniversalOrbitMapBuilder {

	private static final String CENTRE_OF_MASS = "COM";
	
	private final List<String> mapData;
	
	private Map<String, Planet> planets;
	
	public UniversalOrbitMapBuilder(List<String> mapData) {
		this.mapData = mapData;
	}

	public UniversalOrbitMap build() {
		for (String tuple : mapData) {
			String[] tuplePlanets = tuple.split(Pattern.quote(")"));
			String orbitedStr = tuplePlanets[0];
			String orbitingStr = tuplePlanets[1];
			Planet orbited = getPlanet(orbitedStr);
			Planet orbiting = getPlanet(orbitingStr);
			orbiting.setOrbits(orbited);
			orbited.addToOrbits(orbiting);
		}
		return new UniversalOrbitMap(planets.get(CENTRE_OF_MASS));
	}

	private Planet getPlanet(String nameStr) {
		Planet planet;
		if (null == planets) {
			planets = new HashMap<>();
		}
		if (planets.containsKey(nameStr)) {
			planet = planets.get(nameStr);
		} else {
			planet = new Planet(nameStr);
			planets.put(nameStr, planet);
		}
		return planet;
	}
}
