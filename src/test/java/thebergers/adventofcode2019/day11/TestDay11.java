package thebergers.adventofcode2019.day11;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import thebergers.adventofcode2019.hullpainter.Hull;
import thebergers.adventofcode2019.hullpainter.PaintingRobot;
import thebergers.adventofcode2019.intcodecomputer.*;

public class TestDay11 {

	@DisplayName("Test robot navigation")
	@Test
	public void testRobotNavigation() throws Exception {
		Long expectedPaintedPanels = 6L;
		PaintingRobot paintingRobot = getRobot();
		paintingRobot.start();
		IntcodeComputerResult result = paintingRobot.doProcessing();
		long paintedPanels = paintingRobot.getPaintedPanels();
		assertThat(paintedPanels).as("Check painted panels").isEqualTo(expectedPaintedPanels);
	}

	@DisplayName("Test view hull")
	@Test
	public void viewHull() throws Exception {
		List<String> expected = Stream.of(
			"..#",
			"..#",
			"##."
		)
		.collect(Collectors.toList());
		PaintingRobot paintingRobot = getRobot();
		paintingRobot.start();
		IntcodeComputerResult result = paintingRobot.doProcessing();
		Hull hull = paintingRobot.getHull();
		List<String> actual = hull.visualise();
		actual.stream().forEach(System.out::println);
		assertThat(actual).as("Compare visualisations").isEqualTo(expected);
	}

	private List<IntcodeComputerResult> getOutputs() {
		Integer sn = 1;
		String nm = "Fake IntcodeComputer";
		return 	Stream.of(
			new IntcodeComputerResult(sn, nm, "1", false),
			new IntcodeComputerResult(sn, nm, "1,0", false),
			new IntcodeComputerResult(sn, nm, "1,0,0,", false),
			new IntcodeComputerResult(sn, nm, "1,0,0,0", false),
			new IntcodeComputerResult(sn, nm, "1,0,0,0,1", false),
			new IntcodeComputerResult(sn, nm, "1,0,0,0,1,0", false),
			new IntcodeComputerResult(sn, nm, "1,0,0,0,1,0,1", false),
			new IntcodeComputerResult(sn, nm, "1,0,0,0,1,0,1,0", false),
			new IntcodeComputerResult(sn, nm, "1,0,0,0,1,0,1,0,0", false),
			new IntcodeComputerResult(sn, nm, "1,0,0,0,1,0,1,0,0,1", false),
			new IntcodeComputerResult(sn, nm, "1,0,0,0,1,0,1,0,0,1,1", false),
			new IntcodeComputerResult(sn, nm, "1,0,0,0,1,0,1,0,0,1,1,0", false),
			new IntcodeComputerResult(sn, nm, "1,0,0,0,1,0,1,0,0,1,1,0,1", false),
			new IntcodeComputerResult(sn, nm, "1,0,0,0,1,0,1,0,0,1,1,0,1,0", true))
			.collect(Collectors.toList());
	}

	private PaintingRobot getRobot() {
		FakeIntcodeComputerBuilder builder = FakeIntcodeComputerBuilder.newInstance();
		builder = builder.setTestResults(getOutputs());
		IntcodeComputerInterface computer = builder.build();
		return new PaintingRobot(computer);
	}
}
