package thebergers.adventofcode2019.monitoringstation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.TreeMap;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AsteroidBelt {

	private static final Logger LOG = LoggerFactory.getLogger(AsteroidBelt.class);
	
	private final List<Asteroid> asteroids;
	
	public AsteroidBelt() {
		this.asteroids = new ArrayList<>();
	}
	
	public void addAsteroid(Asteroid asteroid) {
		asteroids.add(asteroid);
	}
	
	public Asteroid findMonitoringStationLocation() {
		Map<Asteroid, Long> visibleAsteroids = new HashMap<>();
		asteroids.stream()
			.forEach(asteroid -> {
				Long visibleAsteroidCount = countVisibleAsteroids(asteroid);
				asteroid.setVisibleAsteroids(visibleAsteroidCount);
				visibleAsteroids.put(asteroid, visibleAsteroidCount);
			});			
		
		Optional<Asteroid> asteroidOpt = visibleAsteroids
				.entrySet()
				.stream()
				.sorted((a1, a2) -> a2.getValue().compareTo(a1.getValue()))
				.map(Entry::getKey)
				.findFirst();
		
		if (asteroidOpt.isPresent()) {
			return asteroidOpt.get();
		}
		return null;
	}
	
	private Long countVisibleAsteroids(Asteroid asteroid) {
		LOG.info("{}", asteroid);
		Map<Double, List<Asteroid>> angles = plotAsteroids(asteroid);
		
		LOG.info("{}", logAngles(angles));
		return angles.entrySet().parallelStream().count();
	}
	
	private Map<Double, List<Asteroid>> plotAsteroids(Asteroid asteroid) {
		Map<Double, List<Asteroid>> angles = new TreeMap<>();
		asteroids.stream()
		.filter(a1 -> !a1.equals(asteroid))
		.forEach(a1 -> {
			Double angle = calculateAngle(asteroid, a1);
			storeAngle(angles, a1, angle);
		});
		return angles;
	}

	private Map<Double, List<Asteroid>> storeAngle(Map<Double, List<Asteroid>> angles, Asteroid a1, Double angle) {
		List<Asteroid> asteroidList;
		if (angles.containsKey(angle)) {
			asteroidList = angles.get(angle);
		} else {
			asteroidList = new ArrayList<>();
		}
		asteroidList.add(a1);
		angles.put(angle, asteroidList);
		return angles;
	}

	private Double calculateAngle(Asteroid asteroid, Asteroid a1) {
		Double xDiff = a1.getX().doubleValue() - asteroid.getX().doubleValue();
		Double yDiff = a1.getY().doubleValue() - asteroid.getY().doubleValue();
		Double angle = Math.toDegrees(Math.atan2(yDiff, xDiff)) + 90.0;
		if (angle < 0) {
			angle += 360;
		}
		return angle; //(angle + 360.0) % 360.0;
	}
	
	private String logAngles(Map<Double, List<Asteroid>> angles) {
		StringBuilder builder = new StringBuilder();
		builder.append(String.format("Map.size(): %d: ", angles.size()));
		angles.entrySet()
			.stream()
			.forEach(entry -> builder.append(String.format("Angle: %f, Asteroid Count: %d, ",
					entry.getKey(), entry.getValue().size())));
		return builder.toString();
	}

	public List<Asteroid> vaporizeAsteroids(Asteroid monitoringStation) {
		List<Asteroid> vaporized = new ArrayList<>();
		Map<Double, List<Asteroid>> angles = plotAsteroids(monitoringStation);
		while (true) {
			TreeSet<Double> angleSet = new TreeSet<>(angles.keySet());
			Iterator<Double> angleSetIter = angleSet.iterator();
			while (angleSetIter.hasNext()) {
				Double angle = angleSetIter.next();
				List<Asteroid> asteroidList = angles.get(angle);
				Optional<Asteroid> toVaporizeOpt = asteroidList
						.stream()
						.sorted((a1, a2) -> a1.distanceFrom(monitoringStation).compareTo(
								a2.distanceFrom(monitoringStation)))
						.findFirst();
				if (toVaporizeOpt.isPresent()) {
					Asteroid toVaporize = toVaporizeOpt.get();
					vaporized.add(toVaporize);
					asteroidList.remove(toVaporize);
					angles.put(angle, asteroidList);
				}
				if (asteroidList.isEmpty()) {
					angles.remove(angle);
				}
			}
			if (angles.isEmpty()) {
				break;
			}
		}
		return vaporized;
	}
}
