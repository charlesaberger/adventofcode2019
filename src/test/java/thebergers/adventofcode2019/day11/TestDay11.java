package thebergers.adventofcode2019.day11;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import thebergers.adventofcode2019.hullpainter.PaintingRobot;
import thebergers.adventofcode2019.intcodecomputer.*;

public class TestDay11 {

	@DisplayName("Test robot navigation")
	@Test
	public void testRobotNavigation() throws Exception {
		List<Integer> input = Arrays.asList(new Integer[] { 0, 0, 0, 0, 1, 0, 0 });
		Integer sn = 1;
		String nm = "Fake IntcodeComputer";
		List<IntcodeComputerResult> outputs = Stream.of(
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

		Long expectedPaintedPanels = 6L;
		FakeIntcodeComputerBuilder builder = FakeIntcodeComputerBuilder.newInstance();
		builder = builder.setTestResults(outputs);
		IntcodeComputerInterface computer = builder.build();
		PaintingRobot paintingRobot = new PaintingRobot(computer);
		paintingRobot.start();
		IntcodeComputerResult result = paintingRobot.doProcessing();
		long paintedPanels = paintingRobot.getPaintedPanels();
		assertThat(paintedPanels).as("Check painted panels").isEqualTo(expectedPaintedPanels);
	}
}
