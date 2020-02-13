package thebergers.adventofcode2019.intcodecomputer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class FakeIntcodeComputer implements IntcodeComputerInterface {

	private static final Logger LOG = LoggerFactory.getLogger(FakeIntcodeComputer.class);

	private final String name;

	private final Integer sequenceNumber;

	private final List<IntcodeComputerResult> testResults;

	private BlockingQueue<Long> input = new LinkedBlockingQueue<>();

	private List<Long> output = new ArrayList<>();

	private PropertyChangeSupport support;

	FakeIntcodeComputer(String name, List<IntcodeComputerResult> testResults) {
		this.sequenceNumber = 1;
		this.name = name;
		this.support = new PropertyChangeSupport(this);
		this.testResults = testResults;
	}

	@Override
	public IntcodeComputerResult processOpcodes() {
		IntcodeComputerResult result = null;
		while (!testResults.isEmpty()) {
			try {
				LOG.info("Waiting for input...");
				Long inputValue = input.take();
				LOG.info("Received input {}", inputValue);
				for (int i = 0; i < 2; i++) {
					result = testResults.remove(0);
					if (support.getPropertyChangeListeners().length > 0) {
						if (!testResults.isEmpty()) {
							support.firePropertyChange("testResult",
								IntcodeComputerResult.errorInstance(), result);
						}
					}
				}
				if (testResults.isEmpty()) {
					break;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public void addInput(Long input) {
		this.input.add(input);
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		support.addPropertyChangeListener(listener);
	}

	@Override
	public void run() {
		IntcodeComputerResult result = processOpcodes();
		if (support.getPropertyChangeListeners().length > 0) {
			support.firePropertyChange("result", IntcodeComputerResult.errorInstance(), result);
		}
	}

	@Override
	public String getName() {
		return name;
	}
}
