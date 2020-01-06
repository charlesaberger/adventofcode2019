package thebergers.adventofcode2019.day10;

import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGiantLaser() {
		List<String> mapData = (List<String>)generateTestMap5();
		AsteroidBelt asteroidBelt = AsteroidBeltBuilder.newInstance().setMapData(mapData).build();
		Asteroid monitoringStation = asteroidBelt.findMonitoringStationLocation();
		List<Asteroid> vaporized = asteroidBelt.vaporizeAsteroids(monitoringStation);
		SoftAssertions.assertSoftly(softly -> {
			softly.assertThat(vaporized.get(1 - 1)).as("1st").isEqualTo(new Asteroid(11, 12));
			softly.assertThat(vaporized.get(2 - 1)).as("2nd").isEqualTo(new Asteroid(12, 1));
			softly.assertThat(vaporized.get(3 - 1)).as("3rd").isEqualTo(new Asteroid(12, 2));
			softly.assertThat(vaporized.get(10 - 1)).as("10th").isEqualTo(new Asteroid(12, 8));
			softly.assertThat(vaporized.get(20 - 1)).as("20th").isEqualTo(new Asteroid(16, 0));
			softly.assertThat(vaporized.get(50 - 1)).as("50th").isEqualTo(new Asteroid(16, 9));
			softly.assertThat(vaporized.get(100 - 1)).as("100th").isEqualTo(new Asteroid(10, 16));
			softly.assertThat(vaporized.get(199 - 1)).as("199th").isEqualTo(new Asteroid(9, 6));
			softly.assertThat(vaporized.get(200 - 1)).as("200th").isEqualTo(new Asteroid(8, 2));
			softly.assertThat(vaporized.get(201 - 1)).as("201st").isEqualTo(new Asteroid(10, 9));
			softly.assertThat(vaporized.get(299 - 1)).as("299th").isEqualTo(new Asteroid(11, 1));
			softly.assertThat(vaporized.get(200 - 1).getCoordinates()).as("200th coordinates").isEqualTo(802);
		});
	}
	
	@Test
	public void testGiantLaser2() {
		List<String> mapData = new ArrayList<>();
		mapData.add(".#....#####...#..");
		mapData.add("##...##.#####..##");
		mapData.add("##...#...#.#####.");
		mapData.add("..#.....#ß...###..");
		mapData.add("..#.#.....#....##");
		AsteroidBelt asteroidBelt = AsteroidBeltBuilder.newInstance().setMapData(mapData).build();
		Asteroid monitoringStation = new Asteroid(8, 3); //asteroidBelt.findMonitoringStationLocation();
		List<Asteroid> vaporized = asteroidBelt.vaporizeAsteroids(monitoringStation);
		SoftAssertions.assertSoftly(softly -> {
			softly.assertThat(monitoringStation).as("MS").isEqualTo(new Asteroid(8, 3));
			softly.assertThat(vaporized.get(1 - 1)).as("1st").isEqualTo(new Asteroid(8, 1));
			softly.assertThat(vaporized.get(2 - 1)).as("2nd").isEqualTo(new Asteroid(9, 0));
			softly.assertThat(vaporized.get(3 - 1)).as("3rd").isEqualTo(new Asteroid(9, 1));
		});
	}
}
