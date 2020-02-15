package thebergers.adventofcode2019.day12;

import java.util.List;

import thebergers.adventofcode2019.jupiter.Universe;
import thebergers.adventofcode2019.jupiter.UniverseHelper;
import thebergers.adventofcode2019.utils.Utils;

public class Day12Part2 {

	public static void main(String[] args) throws Exception {
		String fileName = "./src/main/resources/day12/input.txt";
		List<String> moonInfo = Utils.getDataFromFile(fileName);
		Universe universe = UniverseHelper.getUniverse(moonInfo);
		Long steps = universe.simulateAndCompare();
		System.out.format("Number of steps = %d%n", steps);
	}
}
