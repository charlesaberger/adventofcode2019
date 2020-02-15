package thebergers.adventofcode2019.jupiter;

import java.util.List;
import java.util.stream.Collectors;

public class UniverseHelper {

	public static Universe getUniverse(List<String> moonInfoList) {
		List<Moon> moons = moonInfoList
			.stream()
			.map(moonInfoStr -> createMoon(moonInfoStr, moonInfoList.indexOf(moonInfoStr)))
			.collect(Collectors.toList());
		return new Universe(moons);
	}

	public static Velocity initialVelocity() {
		return new Velocity(0, 0, 0);
	}

	private static Moon createMoon(String moonInfo, Integer index) {
		moonInfo = moonInfo
			.replace("<", "")
			.replace(">", "")
			.replace(" ", "");
		String[] coordinates = moonInfo.split(",");
		Integer x = getCoordinate(coordinates[0]);
		Integer y = getCoordinate(coordinates[1]);
		Integer z = getCoordinate(coordinates[2]);
		String name = String.format("Moon %d", index + 1);
		Position position = new Position(x, y, z);
		return new Moon(name, position, initialVelocity());
	}

	private static Integer getCoordinate(String coordinateStr) {
		String coordinateValue = coordinateStr.split("=")[1];
		return Integer.parseInt(coordinateValue);
	}

	private UniverseHelper() {

	}
}
