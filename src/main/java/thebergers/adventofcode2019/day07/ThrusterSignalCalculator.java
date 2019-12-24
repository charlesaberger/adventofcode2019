package thebergers.adventofcode2019.day07;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import thebergers.adventofcode2019.intcodecomputer.IntcodeComputerResult;

public class ThrusterSignalCalculator {
	
	private static final Logger LOG = LoggerFactory.getLogger(ThrusterSignalCalculator.class);

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

	public Integer calculateThrust() throws InterruptedException, ExecutionException {
		IntcodeComputerResult result;
		result = ampA.calculateThrust(0);
		result = ampB.calculateThrust(result.getOutput());
		result = ampC.calculateThrust(result.getOutput());
		result = ampD.calculateThrust(result.getOutput());
		return ampE.calculateThrust(result.getOutput()).getOutput();
	}

	public Integer calculateThrustWithFeedback(Integer input) {
		LOG.info("Phase setting: {}, starting calculation...", phaseSetting);
		Integer ampAInput = input;
		/*while (true) {
			Integer ampBInput = ampA.calculateThrust(ampAInput);
			Integer ampCInput = ampB.calculateThrust(ampBInput);
			Integer ampDInput = ampC.calculateThrust(ampCInput);
			Integer ampEInput = ampD.calculateThrust(ampDInput);
			ampAInput = ampE.calculateThrust(ampEInput);
			if (ampE.isTerminated()) {
				break;
			}
		}*/
		//Integer result = 0; //ampE.getOutput();
		//LOG.info("Phase setting: {}, result = {}", phaseSetting, result);
		//return result;
		return 0;
	}
}
