package thebergers.adventofcode2019.intcodecomputer;

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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultiThreadedIntcodeComputerRunner implements IntcodeComputerRunner, PropertyChangeListener {
	
	private static final Logger LOG = LoggerFactory.getLogger(MultiThreadedIntcodeComputerRunner.class);

	private final List<IntcodeComputerBuilder> builders;
	
	private Map<String, IntcodeComputerInterface> computers = new HashMap<>();
	
	private Map<String, String> connections = new HashMap<>();
	
	private BlockingQueue<IntcodeComputerResult> results;
	
	public MultiThreadedIntcodeComputerRunner(List<IntcodeComputerBuilder> builders) {
		this.builders = builders;
		initialise();
	}
	
	@Override
	public void initialise() {
		results = new LinkedBlockingQueue<>();
		builders.stream()
		.forEach(builder -> {
			if (!StringUtils.isEmpty(builder.getConnectsTo())) {
				connections.put(builder.getName(), builder.getConnectsTo());
			}
		});
		
		builders.stream()
		.map(IntcodeComputerBuilder::build)
		.forEach(computer -> {
			computer.addPropertyChangeListener(this);
			computers.put(computer.getName(), computer);
		});
	}

	@Override
	public void start() {
		Executor pool = Executors.newFixedThreadPool(computers.size());
		computers.entrySet()
		.stream()
		.forEach(entry -> pool.execute(entry.getValue()));
	}

	@Override
	public IntcodeComputerResult doProcessing() throws InterruptedException, ExecutionException {
		IntcodeComputerResult finalResult;
		while (true) {
			LOG.info("Waiting for output...");
			IntcodeComputerResult result = results.take();
			if (result.isTerminated() && result.getSequenceNumber().equals(computers.size())) {
				finalResult = result;
				break;
			}
			String computerName = result.getName();
			String nextComputer = connections.get(computerName);
			IntcodeComputerInterface computer = computers.get(nextComputer);
			if (null != computer) {
				computer.addInput(result.getOutput());
			}
		}
		return finalResult;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		IntcodeComputerResult result = (IntcodeComputerResult)evt.getNewValue();
		results.add(result);
	}
}
