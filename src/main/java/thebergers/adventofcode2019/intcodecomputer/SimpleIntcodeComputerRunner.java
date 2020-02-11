package thebergers.adventofcode2019.intcodecomputer;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class SimpleIntcodeComputerRunner implements IntcodeComputerRunner {

	private IntcodeComputerInterface intcodeComputer;
	
	public SimpleIntcodeComputerRunner(IntcodeComputerBuilder builder) {
		this.intcodeComputer = builder.build();
	}
	
	@Override
	public void initialise() {
		// do nothing
	}

	@Override
	public void start() {
		// do nothing
	}

	@Override
	public IntcodeComputerResult doProcessing() throws InterruptedException, ExecutionException {
		return CompletableFuture.supplyAsync(() -> intcodeComputer.processOpcodes()).get();
	}

}
