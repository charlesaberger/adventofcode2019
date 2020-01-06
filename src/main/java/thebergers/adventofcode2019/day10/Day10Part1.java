package thebergers.adventofcode2019.day10;

import java.util.List;

import thebergers.adventofcode2019.monitoringstation.Asteroid;
import thebergers.adventofcode2019.monitoringstation.AsteroidBelt;
import thebergers.adventofcode2019.monitoringstation.AsteroidBeltBuilder;
import thebergers.adventofcode2019.utils.Utils;

public class Day10Part1 {

	public static void main(String[] args) throws Exception {
		String fileName = "./src/main/resources/day10/input.txt";
		List<String> mapData = Utils.getDataFromFile(fileName);
		AsteroidBelt asteroidBelt = AsteroidBeltBuilder.newInstance().setMapData(mapData).build();
		Asteroid asteroid = asteroidBelt.findMonitoringStationLocation();
		System.out.format("Asteroid: %s, visibleAsteroids=%d%n", asteroid, asteroid.getVisibleAsteroids());
	}

}
