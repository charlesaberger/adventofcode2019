package thebergers.adventofcode2019.day06;

import java.io.IOException;
import java.util.List;

import thebergers.adventofcode2019.universalorbitmap.UniversalOrbitMap;
import thebergers.adventofcode2019.universalorbitmap.UniversalOrbitMapBuilder;
import thebergers.adventofcode2019.utils.Utils;

public class Day06Part2 {

	public static void main(String[] args) throws IOException {
		String fileName = "./src/main/resources/day06/input.txt";
		List<String> mapData = Utils.getDataFromFile(fileName);
		UniversalOrbitMap uom = new UniversalOrbitMapBuilder(mapData).build();
		System.out.format("Orbit Transfers = %d%n", uom.countOrbitalTransfers("YOU", "SAN"));
	}

}
