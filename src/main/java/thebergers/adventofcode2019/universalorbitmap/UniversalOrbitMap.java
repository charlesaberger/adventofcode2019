package thebergers.adventofcode2019.universalorbitmap;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UniversalOrbitMap {
	
	private static final Logger LOG = LoggerFactory.getLogger(UniversalOrbitMap.class); 

	private final Planet centreOfMass;
	
	public UniversalOrbitMap(Planet planet) {
		this.centreOfMass = planet;
	}

	public Integer calculateOrbitCountChecksum() {
		return visitPlanet(centreOfMass, 0);
	}
	
	private Integer visitPlanet(Planet planet, Integer totalOrbits) {
		LOG.info("Visiting planet {}, totalOrbits={}", planet.getName(), totalOrbits);
		Integer numOrbits = countOrbits(planet, 0);
		totalOrbits += numOrbits;
		LOG.info("Planet {}, numOrbits={}, totalOrbits={}", planet.getName(), numOrbits, totalOrbits);
		Iterator<Planet> planetIter = planet.getOrbitedBy().iterator();
		while (planetIter.hasNext()) {
			totalOrbits = visitPlanet(planetIter.next(), totalOrbits);
		}
		return totalOrbits;
	}

	private Integer countOrbits(Planet planet, Integer orbitCount) {
		LOG.info("At planet {}, orbitCount={}", planet.getName(), orbitCount);
		if (null != planet.getOrbits()) {
			orbitCount++;
			orbitCount = countOrbits(planet.getOrbits(), orbitCount);
		}
		return orbitCount;
	}
}
