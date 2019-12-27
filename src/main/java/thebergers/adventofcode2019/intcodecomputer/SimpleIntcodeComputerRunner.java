package thebergers.adventofcode2019.intcodecomputer;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class SimpleIntcodeComputerRunner implements IntcodeComputerRunner {

	private IntcodeComputer intcodeComputer;
	
	private final String name;
	
	private final String program;
	
	public SimpleIntcodeComputerRunner(IntcodeComputerBuilder builder) {
		this.name = builder.getName();
		this.program = builder.getProgram();
		initialise();
	}
	
	@Override
	public void initialise() {
		intcodeComputer = new IntcodeComputer(1, name, program);
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
