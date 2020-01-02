package thebergers.adventofcode2019.day09;

import thebergers.adventofcode2019.intcodecomputer.IntcodeComputerBuilder;
import thebergers.adventofcode2019.intcodecomputer.IntcodeComputerResult;
import thebergers.adventofcode2019.intcodecomputer.IntcodeComputerRunner;
import thebergers.adventofcode2019.intcodecomputer.IntcodeComputerRunnerBuilder;
import thebergers.adventofcode2019.utils.Utils;

public class Day09Part1 {

	public static void main(String[] args) throws Exception {
		String fileName = "./src/main/resources/day09/input.txt";
		String program = Utils.getDataFromFile(fileName).get(0);
		IntcodeComputerRunner runner = IntcodeComputerRunnerBuilder.newInstance()
				.addBuilder(IntcodeComputerBuilder.newInstance()
						.setName("BOOST")
						.setProgram(program)
						.setSequenceNumber(1)
						.addInput(1L))
				.build();
		IntcodeComputerResult result = runner.doProcessing();
		System.out.format("BOOST keycode = %d%n", result.getOutput());
	}
}
