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

	private static Moon createMoon(String moonInfo, Integer index) {
		moonInfo = moonInfo
			.replace("<", "")
			.replace(">", "")
			.replace(" ", "");
		String[] coordinates = moonInfo.split(",");
		Integer xPos = getCoordinate(coordinates[0]);
		Integer yPos = getCoordinate(coordinates[1]);
		Integer zPos = getCoordinate(coordinates[2]);
		String name = String.format("Moon %d", index + 1);
		Axis x = new Axis(xPos);
		Axis y = new Axis(yPos);
		Axis z = new Axis(zPos);
		return new Moon(name, x, y, z);
	}

	private static Integer getCoordinate(String coordinateStr) {
		String coordinateValue = coordinateStr.split("=")[1];
		return Integer.parseInt(coordinateValue);
	}

	private UniverseHelper() {

	}
}
