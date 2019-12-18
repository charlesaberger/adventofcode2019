package thebergers.adventofcode2019.day06;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import thebergers.adventofcode2019.universalorbitmap.UniversalOrbitMap;
import thebergers.adventofcode2019.universalorbitmap.UniversalOrbitMapBuilder;

public class TestDay06 {

	@Test
	public void testCalculateOrbitCountChecksum() {
		UniversalOrbitMap uom = new UniversalOrbitMapBuilder(getMapData()).build();
		assertThat(uom.calculateOrbitCountChecksum()).as("Calculate universe orbit count checksum").isEqualTo(42);
	}
	
	@Test
	public void testCountOrbitalTransfers() {
		UniversalOrbitMap uom = new UniversalOrbitMapBuilder(getMapDataForPart2()).build();
		assertThat(uom.countOrbitalTransfers("YOU", "SAN")).as("Count Orbital Transfers").isEqualTo(4);
	}
	
	private List<String> getMapData() {
		return Stream.of("J)K","C)D","K)L","G)H","D)E","E)F","COM)B","B)G","D)I","E)J","B)C")
				.collect(Collectors.toList());
	}
	
	private List<String> getMapDataForPart2() {
		List<String> mapData = getMapData();
		mapData.add("K)YOU");
		mapData.add("I)SAN");
		return mapData;
	}
}
