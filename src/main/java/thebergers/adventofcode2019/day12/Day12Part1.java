package thebergers.adventofcode2019.day12;

import java.util.List;

import thebergers.adventofcode2019.intcodecomputer.IntcodeComputerBuilder;
import thebergers.adventofcode2019.intcodecomputer.IntcodeComputerResult;
import thebergers.adventofcode2019.intcodecomputer.IntcodeComputerRunner;
import thebergers.adventofcode2019.intcodecomputer.IntcodeComputerRunnerBuilder;
import thebergers.adventofcode2019.jupiter.Universe;
import thebergers.adventofcode2019.jupiter.UniverseHelper;
import thebergers.adventofcode2019.utils.Utils;

public class Day12Part1 {

	public static void main(String[] args) throws Exception {
		String fileName = "./src/main/resources/day12/input.txt";
		List<String> moonInfo = Utils.getDataFromFile(fileName);
		Universe universe = UniverseHelper.getUniverse(moonInfo);
		universe.simulateMotion(1000);
		System.out.format("Total energy = %d%n", universe.calculateEnergy());
	}
}
