package thebergers.adventofcode2019.day05;

import java.io.IOException;

import thebergers.adventofcode2019.day02.IntcodeComputer;
import thebergers.adventofcode2019.utils.Utils;

public class Day5Part1 {

	public static void main(String[] args) throws IOException {
		String fileName = "./src/main/resources/day05/input.txt";
		String program = Utils.getDataFromFile(fileName).get(0);
		IntcodeComputer ic = new IntcodeComputer(program);
		ic.processOpcodes();
	}
}
