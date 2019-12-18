package thebergers.adventofcode2019.day02;

import java.io.IOException;
import java.util.List;

import thebergers.adventofcode2019.intcodecomputer.IntcodeComputer;
import thebergers.adventofcode2019.intcodecomputer.UnknownOpcodeException;
import thebergers.adventofcode2019.utils.Utils;

public class DayTwo {

	public static void main(String[] args) throws IOException, UnknownOpcodeException {
		String fileName = "./src/main/resources/daytwo/input.txt";
		List<String> input = Utils.getDataFromFile(fileName);
		IntcodeComputer intcodeComputer = new IntcodeComputer(input.get(0));
		intcodeComputer.setCode(1, 12);
		intcodeComputer.setCode(2, 2);
		intcodeComputer.processOpcodes();
		System.out.println(intcodeComputer.getPosition(0));
	}
}
