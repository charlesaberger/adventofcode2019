package thebergers.adventofcode2019.day05;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import thebergers.adventofcode2019.intcodecomputer.IntcodeComputer;
import thebergers.adventofcode2019.intcodecomputer.IntcodeComputerResult;
import thebergers.adventofcode2019.utils.Utils;

public class Day5Part2 {

	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
		String fileName = "./src/main/resources/day05/input.txt";
		String program = Utils.getDataFromFile(fileName).get(0);
		IntcodeComputer ic = new IntcodeComputer(program);
		CompletableFuture<IntcodeComputerResult> future = CompletableFuture.supplyAsync(() -> {
			ic.addInput(5);
			return ic.processOpcodes();
		});
		IntcodeComputerResult result = future.get();
		System.out.format("Result = %d%n", result.getOutput());
	}
}
