package thebergers.adventofcode2019.day03;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class ControlPanel {

	private final List<Wire> wires;
	
	public ControlPanel(List<String> wires) {
		this.wires = generateWires(wires);
	}

	private List<Wire> generateWires(List<String> wireStrings) {
		return wireStrings
			.stream()
			.map(Wire::new)
			.collect(Collectors.toList());
	}

	public Point findClosestIntersection() {
		List<Point> intersections = findIntersections();
		return intersections
		.stream()
		.sorted((p1, p2) -> { return p1.distanceFromOrigin().compareTo(p2.distanceFromOrigin()); })
		.collect(Collectors.toList())
		.get(0);
	}
	
	public List<Point> findIntersections() {
		Map<Point, List<UUID>> intersections = new HashMap<>();
		for (Wire wire : wires) {
			for (Point point : wire.getPoints()) {
				if (point.getX().equals(0) && point.getY().equals(0)) {
					continue;
				}
				List<UUID> uuids;
				if (intersections.containsKey(point)) {
					uuids = intersections.get(point);
				} else {
					uuids = new LinkedList<>();
				}
				UUID wireId = wire.getId();
				if (!uuids.contains(wireId)) {
					uuids.add(wireId);
				}
				intersections.put(point, uuids);
			}
		}
		final List<Point> intersectionList = new LinkedList<>();
		intersections.forEach((point, uuids) -> {
			if (uuids.size() > 1) {
				intersectionList.add(point);
			}
		});
		return intersectionList;
	}

}
