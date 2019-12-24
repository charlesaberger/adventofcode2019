package thebergers.adventofcode2019.day07;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import thebergers.adventofcode2019.intcodecomputer.IntcodeComputer;
import thebergers.adventofcode2019.intcodecomputer.IntcodeComputerResult;

public class Amplifier {

	private final Integer phase;
	
	private final IntcodeComputer intcodeComputer;
	
	private IntcodeComputerResult result;
	
	public Amplifier(Integer phase, String program) {
		this.phase = phase;
		this.intcodeComputer = new IntcodeComputer(program);
		this.intcodeComputer.addInput(phase);
	}
	
	public IntcodeComputerResult calculateThrust(Integer input) throws InterruptedException, ExecutionException {
		CompletableFuture<IntcodeComputerResult> future = CompletableFuture.supplyAsync(() -> {
			intcodeComputer.addInput(input);
			return intcodeComputer.processOpcodes();
		});
		result = future.get();
		return result;
	}
	
	public boolean isTerminated() {
		return result.isTerminated();
	}
	
	public Integer getOutput() {
		return result.getOutput();
	}
}
