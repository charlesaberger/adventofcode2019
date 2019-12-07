package thebergers.adventofcode2019.day03;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class ControlPanel {

	private final List<Wire> wires;
	
	private final List<Point> intersections;
	
	public ControlPanel(List<String> wires) {
		this.wires = generateWires(wires);
		this.intersections = findIntersections();
	}

	private List<Wire> generateWires(List<String> wireStrings) {
		return wireStrings
			.stream()
			.map(Wire::new)
			.collect(Collectors.toList());
	}

	public Point findClosestIntersection() {
		return intersections
		.stream()
		.sorted((p1, p2) -> { return p1.distanceFromOrigin().compareTo(p2.distanceFromOrigin()); })
		.collect(Collectors.toList())
		.get(0);
	}
	
	private List<Point> findIntersections() {
		Map<Point, List<UUID>> intersectionsMap = new HashMap<>();
		for (Wire wire : wires) {
			for (Point point : wire.getPoints()) {
				if (point.getX().equals(0) && point.getY().equals(0)) {
					continue;
				}
				List<UUID> uuids;
				if (intersectionsMap.containsKey(point)) {
					uuids = intersectionsMap.get(point);
				} else {
					uuids = new LinkedList<>();
				}
				UUID wireId = wire.getId();
				if (!uuids.contains(wireId)) {
					uuids.add(wireId);
				}
				intersectionsMap.put(point, uuids);
			}
		}
		final List<Point> intersectionList = new LinkedList<>();
		intersectionsMap.forEach((point, uuids) -> {
			if (uuids.size() > 1) {
				intersectionList.add(point);
			}
		});
		return intersectionList;
	}
	
	private Integer calculateTotalSteps(Point destination) {
		Integer steps = 0;
		for (Wire wire : wires) {
			steps += calculateStepsToPoint(wire, destination);
		}
		return steps;
	}
	
	private Integer calculateStepsToPoint(Wire wire, Point destination) {
		Integer steps = 0;
		for (Point point : wire.getPoints()) {
			if (!point.isOrigin()) {
				steps++;
			}
			if (point.equals(destination)) {
				break;
			}
		}
		return steps;
	}

	public Optional<Integer> getMinimumNumSteps() {
		return intersections
			.stream()
			.map(p -> calculateTotalSteps(p))
			.sorted()
			.findFirst();
	}

}
