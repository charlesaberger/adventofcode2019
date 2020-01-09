package thebergers.adventofcode2019.intcodecomputer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class FakeIntcodeComputerRunner implements IntcodeComputerRunner, PropertyChangeListener {

	private List<IntcodeComputerResult> output;
	
	public FakeIntcodeComputerRunner(List<IntcodeComputerResult> results) {
		this.output = results;
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void initialise() {
		// TODO Auto-generated method stub

	}

	@Override
	public void start() {
		Executor pool = Executors.newFixedThreadPool(1);
		
	}

	@Override
	public IntcodeComputerResult doProcessing() throws InterruptedException, ExecutionException {
		// TODO Auto-generated method stub
		return null;
	}

	class FakeIntcodeComputer implements Runnable {
		
		private BlockingQueue<Long> input = new LinkedBlockingQueue<>();
		
		private PropertyChangeSupport support;

		public void addInput(Long value) {
			input.add(value);
		}

		@Override
		public void run() {
			while (true) {
				
			}
		}
	}
}
