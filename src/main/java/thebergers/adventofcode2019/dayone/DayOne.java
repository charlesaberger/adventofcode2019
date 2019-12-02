package thebergers.adventofcode2019.dayone;

import java.io.IOException;
import java.util.List;

import thebergers.adventofcode2019.utils.Utils;

public class DayOne {

	public static void main(String[] args) throws IOException {
		String fileName = "./src/main/resources/dayone/input.txt";
		List<String> input = Utils.getDataFromFile(fileName);
		FuelCounterUpper fcu = new FuelCounterUpper(input);
		System.out.println(String.format("result = %s", fcu.calculateFuel()));
	}

}
