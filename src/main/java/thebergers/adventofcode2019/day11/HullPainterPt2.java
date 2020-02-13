package thebergers.adventofcode2019.day11;

import thebergers.adventofcode2019.hullpainter.Colour;
import thebergers.adventofcode2019.hullpainter.Hull;
import thebergers.adventofcode2019.hullpainter.PaintingRobot;
import thebergers.adventofcode2019.intcodecomputer.IntcodeComputerBuilder;
import thebergers.adventofcode2019.intcodecomputer.IntcodeComputerInterface;
import thebergers.adventofcode2019.utils.Utils;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class HullPainterPt2 {
	public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
		String fileName = "./src/main/resources/day11/input.txt";
		String program = Utils.getDataFromFile(fileName).get(0);
		IntcodeComputerBuilder builder = IntcodeComputerBuilder.newInstance();
		IntcodeComputerInterface computer = builder
			.setName("Hull Painter")
			.setProgram(program)
			.setSequenceNumber(1)
			.build();
		PaintingRobot robot = new PaintingRobot(computer, Colour.WHITE);
		robot.start();
		robot.doProcessing();
		Hull hull = robot.getHull();
		hull.visualise()
			.stream()
			.forEach(System.out::println);
	}
}
