package thebergers.adventofcode2019.hullpainter;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;

import thebergers.adventofcode2019.intcodecomputer.IntcodeComputer;
import thebergers.adventofcode2019.intcodecomputer.IntcodeComputerResult;
import thebergers.adventofcode2019.intcodecomputer.IntcodeComputerRunner;

public class PaintingRobot implements IntcodeComputerRunner, PropertyChangeListener {

	private final Hull hull;
	
	private final IntcodeComputerRunner intcodeComputer;
	
	private BlockingQueue<IntcodeComputerResult> results;
	
	public PaintingRobot(IntcodeComputerRunner runner) {
		this.hull = new Hull();
		this.intcodeComputer = runner;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initialise() {
		results = new LinkedBlockingQueue<>();
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IntcodeComputerResult doProcessing() throws InterruptedException, ExecutionException {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer getPaintedPanels() {
		// TODO Auto-generated method stub
		return null;
	}
}
