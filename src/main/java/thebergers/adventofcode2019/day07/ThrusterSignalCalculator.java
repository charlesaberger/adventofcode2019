package thebergers.adventofcode2019.day07;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ThrusterSignalCalculator {

	private final String phaseSetting;
	
	private final String program;
	
	private Amplifier ampA;
	
	private Amplifier ampB;
	
	private Amplifier ampC;
	
	private Amplifier ampD;
	
	private Amplifier ampE;
	
	public ThrusterSignalCalculator(String phaseSetting, String program) {
		this.phaseSetting = phaseSetting;
		this.program = program;
		buildAmplifiers(phaseSetting);
	}

	private void buildAmplifiers(String phaseSetting) {
		List<Integer> phases = Arrays.asList(phaseSetting.split(","))
				.stream()
				.map(Integer::parseInt)
				.collect(Collectors.toList());
		ampA = new Amplifier(phases.get(0), program);
		ampB = new Amplifier(phases.get(1), program);
		ampC = new Amplifier(phases.get(2), program);
		ampD = new Amplifier(phases.get(3), program);
		ampE = new Amplifier(phases.get(4), program);
	}

	public String getPhaseSetting() {
		return phaseSetting;
	}

	public String getProgram() {
		return program;
	}

	public Integer calculateThrust() {
		return ampE.calculateThrust(
					ampD.calculateThrust(
						ampC.calculateThrust(
							ampB.calculateThrust(
								ampA.calculateThrust(0)))));
	}
	
}
