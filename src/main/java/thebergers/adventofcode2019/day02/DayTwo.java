package thebergers.adventofcode2019.day02;

import java.io.IOException;
import java.util.List;

import thebergers.adventofcode2019.utils.Utils;

public class DayTwo {

	public static void main(String[] args) throws IOException, UnknownOpcodeException {
		String fileName = "./src/main/resources/daytwo/input.txt";
		List<String> input = Utils.getDataFromFile(fileName);
		IntcodeComputer intcodeComputer = new IntcodeComputer(input.get(0));
		System.out.println(intcodeComputer.processOpcodes());
	}
}
