package thebergers.adventofcode2019.intcodecomputer;

import java.beans.PropertyChangeListener;

public interface IntcodeComputerInterface extends Runnable {

	IntcodeComputerResult processOpcodes();

	void addInput(Long input);

	void addPropertyChangeListener(PropertyChangeListener listener);

	void run();

	String getName();
}
