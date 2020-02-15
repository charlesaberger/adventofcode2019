package thebergers.adventofcode2019.day12;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import thebergers.adventofcode2019.jupiter.Universe;
import thebergers.adventofcode2019.jupiter.UniverseHelper;

public class TestDay12 {

	@DisplayName("Examples")
	@ParameterizedTest(name = "{index}: steps: {1}, energy: {2}")
	@MethodSource("getMoonInfo")
	public void examples(List<String> moonInfo, Integer numSteps, Integer expectedEnergy) {
		Universe universe = UniverseHelper.getUniverse(moonInfo);
		universe.simulateMotion(numSteps);
		assertThat(universe.calculateEnergy()).as("Total energy").isEqualTo(expectedEnergy);
	}

	static Stream<Arguments> getMoonInfo() {
		return Stream.of(
			arguments(Arrays.asList(
				"<x=-1, y=0, z=2>",
				"<x=2, y=-10, z=-7>",
				"<x=4, y=-8, z=8>",
				"<x=3, y=5, z=-1>"
			), 10, 179),
			arguments(Arrays.asList(
				"<x=-8, y=-10, z=0>",
				"<x=5, y=5, z=10>",
				"<x=2, y=-7, z=3>",
				"<x=9, y=-8, z=-3>"
			), 100, 1940)
		);
	}
}
