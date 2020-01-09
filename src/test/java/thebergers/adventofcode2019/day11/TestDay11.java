package thebergers.adventofcode2019.day11;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import thebergers.adventofcode2019.hullpainter.PaintingRobot;
import thebergers.adventofcode2019.intcodecomputer.FakeIntcodeComputerRunner;
import thebergers.adventofcode2019.intcodecomputer.IntcodeComputerResult;
import thebergers.adventofcode2019.intcodecomputer.IntcodeComputerRunner;

public class TestDay11 {

	@DisplayName("Test robot navigation")
	@Test
	public void testRobotNavigation() throws Exception {
		List<Integer> input = Arrays.asList(new Integer[] { 0, 0, 0, 0, 1, 0, 0 });
		Integer sn = 1;
		String nm = "Fake IntcodeComputer";
		List<IntcodeComputerResult> outputs = Stream.of(
				new IntcodeComputerResult(sn, nm, "1,0", Arrays.asList(new Long[] { 1L, 0L }), false),
				new IntcodeComputerResult(sn, nm, "0,0", Arrays.asList(new Long[] { 0L, 0L }), false),
				new IntcodeComputerResult(sn, nm, "1,0", Arrays.asList(new Long[] { 1L, 0L }), false),
				new IntcodeComputerResult(sn, nm, "1,0", Arrays.asList(new Long[] { 1L, 0L }), false),
				new IntcodeComputerResult(sn, nm, "0,1", Arrays.asList(new Long[] { 0L, 1L }), false),
				new IntcodeComputerResult(sn, nm, "1,0", Arrays.asList(new Long[] { 1L, 0L }), false),
				new IntcodeComputerResult(sn, nm, "1,0", Arrays.asList(new Long[] { 1L, 0L }), true))
				.collect(Collectors.toList());

		Integer expectedPaintedPanels = 6;
		IntcodeComputerRunner runner = new FakeIntcodeComputerRunner(outputs);
		PaintingRobot paintingRobot = new PaintingRobot(runner);
		IntcodeComputerResult result = paintingRobot.doProcessing();
		Integer paintedPanels = paintingRobot.getPaintedPanels();
		assertThat(paintedPanels).as("Check painted panels").isEqualTo(expectedPaintedPanels);
	}
}
