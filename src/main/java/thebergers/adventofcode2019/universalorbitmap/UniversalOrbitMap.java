package thebergers.adventofcode2019.universalorbitmap;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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

	public Integer countOrbitalTransfers(String fromPlanetName, String toPlanetName) {
		List<String> fromPlanetToOrigin = findPlanetsToOrigin(fromPlanetName);
		List<String> toPlanetToOrigin = findPlanetsToOrigin(toPlanetName);
		LOG.info("fromPlanetToOrigin:{}", fromPlanetToOrigin);
		LOG.info("toPlanetToOrigin:{}", toPlanetToOrigin);
		String intersection = findIntersection(fromPlanetToOrigin, toPlanetToOrigin);
		LOG.info("Intersection is {}", intersection);
		return countTotalSteps(fromPlanetToOrigin, toPlanetToOrigin, intersection);
	}

	private List<String> findPlanetsToOrigin(String name) {
		return returnToCom(findPlanet(centreOfMass, name), new LinkedList<>());
	}

	private List<String> returnToCom(Planet planet, LinkedList<String> linkedList) {
		if (planet.getOrbits() != null) {
			//if (planet)
			linkedList.add(planet.getName());
			return returnToCom(planet.getOrbits(), linkedList);
		}
		return linkedList;
	}

	private Planet findPlanet(Planet planet, String name) {
		LOG.info("Planet {}", planet.getName());
		Iterator<Planet> planetIter = planet.getOrbitedBy().iterator();
		Planet result = planet;
		while (planetIter.hasNext()) {
			result = findPlanet(planetIter.next(), name);
			if (result.getName().equals(name)) {
				LOG.info("Found planet with name {}", name);
				return result;
			}
		}
		return result;
	}

	private String findIntersection(List<String> fromPlanetToOrigin, List<String> toPlanetToOrigin) {
		return toPlanetToOrigin.parallelStream()
				.filter(name -> fromPlanetToOrigin.contains(name))
				.findFirst()
				.get();
	}

	private Integer countTotalSteps(List<String> fromPlanetToOrigin, List<String> toPlanetToOrigin, String intersection) {
		return countSteps(toPlanetToOrigin, intersection) + countSteps(fromPlanetToOrigin, intersection) - 2;
	}
	
	private Integer countSteps(List<String> list, String intersection) {
		Integer steps = 0;
		for (String str: list) {
			if (str.equals(intersection)) {
				break;
			}
			steps++;
		}
		return steps;
	}
}
