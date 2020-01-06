package thebergers.adventofcode2019.day10;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import thebergers.adventofcode2019.monitoringstation.Asteroid;
import thebergers.adventofcode2019.monitoringstation.AsteroidBelt;
import thebergers.adventofcode2019.monitoringstation.AsteroidBeltBuilder;

public class TestDay10 {

	@DisplayName("Test find monitoring station location")
	@ParameterizedTest(name = "{index}:  map size: {0}, expected: {1}, visibleAsteroids: {2}")
	@MethodSource("generateTestMaps")
	public void testFindMonitoringStationLocation(List<String> mapData, Asteroid expected) {
		AsteroidBelt asteroidBelt = AsteroidBeltBuilder.newInstance().setMapData(mapData).build();
		Asteroid asteroid = asteroidBelt.findMonitoringStationLocation();
		SoftAssertions.assertSoftly(softly -> {
			softly.assertThat(asteroid.getX()).as("X coordinate").isEqualTo(expected.getX());
			softly.assertThat(asteroid.getY()).as("Y coordinate").isEqualTo(expected.getY());
			softly.assertThat(asteroid.getVisibleAsteroids()).as("Visible Asteroids").isEqualTo(expected.getVisibleAsteroids());
		});
	}
	
	static Stream<Arguments> generateTestMaps() {
		return Stream.of(
			arguments(generateTestMap1(), new Asteroid("3,4", 8L)),
			arguments(generateTestMap2(), new Asteroid("5,8", 33L)),
			arguments(generateTestMap3(), new Asteroid("1,2", 35L)),
			arguments(generateTestMap4(), new Asteroid("6,3", 41L)),
			arguments(generateTestMap5(), new Asteroid("11,13", 210L))
		);
	}

	private static Object generateTestMap1() {
		List<String> mapData = new ArrayList<>();
		mapData.add(".#..#");
		mapData.add(".....");
		mapData.add("#####");
		mapData.add("....#");
		mapData.add("...##");
		return mapData;
	}
	
	private static Object generateTestMap2() {
		List<String> mapData = new ArrayList<>();
		mapData.add("......#.#.");
		mapData.add("#..#.#....");
		mapData.add("..#######.");
		mapData.add(".#.#.###..");
		mapData.add(".#..#.....");
		mapData.add("..#....#.#");
		mapData.add("#..#....#.");
		mapData.add(".##.#..###");
		mapData.add("##...#..#.");
		mapData.add(".#....####");
		return mapData;
	}
	
	private static Object generateTestMap3() {
		List<String> mapData = new ArrayList<>();
		mapData.add("#.#...#.#.");
		mapData.add(".###....#.");
		mapData.add(".#....#...");
		mapData.add("##.#.#.#.#");
		mapData.add("....#.#.#.");
		mapData.add(".##..###.#");
		mapData.add("..#...##..");
		mapData.add("..##....##");
		mapData.add("......#...");
		mapData.add(".####.###.");
		return mapData;
	}
	
	private static Object generateTestMap4() {
		List<String> mapData = new ArrayList<>();
		mapData.add(".#..#..###");
		mapData.add("####.###.#");
		mapData.add("....###.#.");
		mapData.add("..###.##.#");
		mapData.add("##.##.#.#.");
		mapData.add("....###..#");
		mapData.add("..#.#..#.#");
		mapData.add("#..#.#.###");
		mapData.add(".##...##.#");
		mapData.add(".....#.#..");
		return mapData;
	}
	
	private static Object generateTestMap5() {
		List<String> mapData = new ArrayList<>();
		mapData.add(".#..##.###...#######");
		mapData.add("##.############..##.");
		mapData.add(".#.######.########.#");
		mapData.add(".###.#######.####.#.");
		mapData.add("#####.##.#.##.###.##");
		mapData.add("..#####..#.#########");
		mapData.add("####################");
		mapData.add("#.####....###.#.#.##");
		mapData.add("##.#################");
		mapData.add("#####.##.###..####..");
		mapData.add("..######..##.#######");
		mapData.add("####.##.####...##..#");
		mapData.add(".#####..#.######.###");
		mapData.add("##...#.##########...");
		mapData.add("#.##########.#######");
		mapData.add(".####.#.###.###.#.##");
		mapData.add("....##.##.###..#####");
		mapData.add(".#.#.###########.###");
		mapData.add("#.#.#.#####.####.###");
		mapData.add("###.##.####.##.#..##");
		return mapData;

	}
}
