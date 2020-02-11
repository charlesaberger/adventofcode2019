package thebergers.adventofcode2019.intcodecomputer;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class FakeIntcodeComputerRunner implements IntcodeComputerRunner, PropertyChangeListener {

	private static final Logger LOG = LoggerFactory.getLogger(FakeIntcodeComputerRunner.class);

	private final FakeIntcodeComputerBuilder builder;

	private IntcodeComputerInterface computer;

	private Map<String, String> connections = new HashMap<>();

	private BlockingQueue<IntcodeComputerResult> results;

	private List<IntcodeComputerResult> testResults;

 	public FakeIntcodeComputerRunner(FakeIntcodeComputerBuilder builder, List<IntcodeComputerResult> expectedResults) {
 		this.builder = builder;
 		this.testResults = expectedResults;
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		IntcodeComputerResult result = (IntcodeComputerResult)evt.getNewValue();
		results.add(result);
	}

	@Override
	public void initialise() {
		results = new LinkedBlockingQueue<>();

		IntcodeComputerInterface computer = builder.build();
		computer.addPropertyChangeListener(this);
	}

	@Override
	public void start() {
		Executor pool = Executors.newFixedThreadPool(1);
		pool.execute(computer);
	}

	@Override
	public IntcodeComputerResult doProcessing() throws InterruptedException, ExecutionException {
		IntcodeComputerResult finalResult;
		while (true) {
			LOG.info("Waiting for output...");
			IntcodeComputerResult result = results.take();
			if (result.isTerminated()) {
				finalResult = result;
				break;
			}
			computer.addInput(result.getOutput());
		}
		return finalResult;
	}

}
