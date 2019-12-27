package thebergers.adventofcode2019.day07;

import java.io.IOException;

import thebergers.adventofcode2019.utils.Utils;

public class Day07Part1 {

	public static void main(String[] args) throws IOException {
		String fileName = "./src/main/resources/day07/input.txt";
		String program = Utils.getDataFromFile(fileName).get(0);
		ThrusterSignalController tsc = ThrusterSignalControllerBuilder
				.newInstance()
				.setProgram(program)
				.setPhaseSettingRange(0, 4)
				.build();
		Integer maxThrust = tsc.calculateMaxThrust();
		System.out.format("Max thrust signal = %d%n", maxThrust);
	}
}
