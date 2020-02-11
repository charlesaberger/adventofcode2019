package thebergers.adventofcode2019.day11;

import thebergers.adventofcode2019.hullpainter.PaintingRobot;
import thebergers.adventofcode2019.intcodecomputer.IntcodeComputer;
import thebergers.adventofcode2019.intcodecomputer.IntcodeComputerBuilder;
import thebergers.adventofcode2019.intcodecomputer.IntcodeComputerBuilderInterface;
import thebergers.adventofcode2019.intcodecomputer.IntcodeComputerInterface;
import thebergers.adventofcode2019.utils.Utils;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class HullPainter {
	public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
		String fileName = "./src/main/resources/day11/input.txt";
		String program = Utils.getDataFromFile(fileName).get(0);
		IntcodeComputerBuilder builder = IntcodeComputerBuilder.newInstance();
		IntcodeComputerInterface computer = builder
			.setName("Hull Painter")
			.setProgram(program)
			.setSequenceNumber(1)
			.build();
		PaintingRobot robot = new PaintingRobot(computer);
		robot.start();
		robot.doProcessing();
		long paintedPanels = robot.getPaintedPanels();
		System.out.print(String.format("Painted %d panels%n", paintedPanels));
	}
}
