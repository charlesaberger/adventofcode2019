package thebergers.adventofcode2019.intcodecomputer;

import java.util.concurrent.ExecutionException;

public interface IntcodeComputerRunner {

	public void initialise();
	
	public void start();
	
	public IntcodeComputerResult doProcessing() throws InterruptedException, ExecutionException;
}
