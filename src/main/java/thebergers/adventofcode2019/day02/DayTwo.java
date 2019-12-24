package thebergers.adventofcode2019.day02;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import thebergers.adventofcode2019.intcodecomputer.IntcodeComputer;
import thebergers.adventofcode2019.intcodecomputer.IntcodeComputerResult;
import thebergers.adventofcode2019.intcodecomputer.UnknownOpcodeException;
import thebergers.adventofcode2019.utils.Utils;

public class DayTwo {

	public static void main(String[] args) throws IOException, UnknownOpcodeException, InterruptedException, ExecutionException {
		String fileName = "./src/main/resources/daytwo/input.txt";
		List<String> input = Utils.getDataFromFile(fileName);
		IntcodeComputer intcodeComputer = new IntcodeComputer(input.get(0));
		intcodeComputer.setCode(1, 12);
		intcodeComputer.setCode(2, 2);
		CompletableFuture<IntcodeComputerResult> future = CompletableFuture.supplyAsync(() -> {
			return intcodeComputer.processOpcodes();
		});
		IntcodeComputerResult result = future.get();
		System.out.println(result.getPosition(0));
	}
}
