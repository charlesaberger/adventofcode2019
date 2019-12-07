package thebergers.adventofcode2019.day03;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class TestDayThree {

	@DisplayName("Test wires crossing")
	@ParameterizedTest(name = "{index} => {0}")
	@MethodSource("generateTestWires")
	public void testCrossedWires(TestWires testWires) {
		ControlPanel controlPanel = new ControlPanel(testWires.getWires());
		Point point = controlPanel.findClosestIntersection();
		int distance = point.distanceFromOrigin();
		Optional<Integer> numberOfSteps = controlPanel.getMinimumNumSteps();
		assertThat(distance).as("The distance is correct").isEqualTo(testWires.getExpectedDistance());
		assertThat(numberOfSteps.isPresent()).as("The minimum number of steps is present").isTrue();
		assertThat(numberOfSteps.get()).as("Number of steps is correct").isEqualTo(testWires.getExpectedSteps());
	}
	
	static Stream<Arguments> generateTestWires() {
		return TestWires.getTestWiresStream()
				.map(testWires -> arguments(testWires));
	}
	

	static class TestWires {
		
		private static final List<TestWires> testWires = new ArrayList<>(); 
		
		static {
			List<String> wiresList = new ArrayList<>();
			wiresList.add("R8,U5,L5,D3");
			wiresList.add("U7,R6,D4,L4");
			testWires.add(new TestWires(wiresList, 6, 30));
			List<String> wiresList1 = new ArrayList<>();
			wiresList1.add("R75,D30,R83,U83,L12,D49,R71,U7,L72");
			wiresList1.add("U62,R66,U55,R34,D71,R55,D58,R83");
			testWires.add(new TestWires(wiresList1, 159, 610));
			List<String> wiresList2 = new ArrayList<>();
			wiresList2.add("R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51");
			wiresList2.add("U98,R91,D20,R16,D67,R40,U7,R15,U6,R7");
			testWires.add(new TestWires(wiresList2, 135, 410));
		}
		
		public static Stream<TestWires> getTestWiresStream() {
			return testWires.stream();
		}
		
		private final List<String> wires;

		private final Integer expectedDistance;
		
		private final Integer expectedSteps;
		
		public TestWires(List<String> wires, Integer expectedDistance, Integer expectedSteps) {
			this.expectedDistance = expectedDistance;
			this.wires = wires;
			this.expectedSteps = expectedSteps;
		}
		
		public List<String> getWires() {
			return wires;
		}

		public Integer getExpectedDistance() {
			return expectedDistance;
		}
		
		public Integer getExpectedSteps() {
			return expectedSteps;
		}
		
		public String toString() {
			return String.format("wire1: %s, expectedDistance: %d, expectedSteps: %d",
					wires.get(0), expectedDistance, expectedSteps);
		}
	}
}
