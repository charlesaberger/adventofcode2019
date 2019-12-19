package thebergers.adventofcode2019.day07;

import thebergers.adventofcode2019.intcodecomputer.IntcodeComputer;

public class Amplifier {

	private final Integer phase;
	
	private final IntcodeComputer intcodeComputer;
	
	public Amplifier(Integer phase, String program) {
		this.phase = phase;
		this.intcodeComputer = new IntcodeComputer(program);
		this.intcodeComputer.addInput(phase);
		this.intcodeComputer.setOutputMode(IntcodeComputer.OutputMode.SAVE);
	}
	
	public Integer calculateThrust(Integer input) {
		intcodeComputer.addInput(input);
		intcodeComputer.processOpcodes();
		return intcodeComputer.getOutput();
	}
}
