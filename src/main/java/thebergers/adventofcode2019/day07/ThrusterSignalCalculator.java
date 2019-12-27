package thebergers.adventofcode2019.day07;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import thebergers.adventofcode2019.intcodecomputer.IntcodeComputerBuilder;
import thebergers.adventofcode2019.intcodecomputer.IntcodeComputerResult;
import thebergers.adventofcode2019.intcodecomputer.IntcodeComputerRunner;
import thebergers.adventofcode2019.intcodecomputer.IntcodeComputerRunnerBuilder;

public class ThrusterSignalCalculator {
	
	private static final Logger LOG = LoggerFactory.getLogger(ThrusterSignalCalculator.class);

	private final String phaseSetting;
	
	private final String program;
	
	private final boolean useFeedback;
	
	private final IntcodeComputerRunner runner;
	
	public ThrusterSignalCalculator(String phaseSetting, String program, boolean useFeedback) {
		this.phaseSetting = phaseSetting;
		this.program = program;
		this.useFeedback = useFeedback;
		this.runner = buildAmplifiers(phaseSetting);
	}

	private IntcodeComputerRunner buildAmplifiers(String phaseSetting) {
		IntcodeComputerRunnerBuilder builder = IntcodeComputerRunnerBuilder.newInstance();
		List<Integer> phases = Arrays.asList(phaseSetting.split(","))
			.stream()
			.map(Integer::parseInt)
			.collect(Collectors.toList());
		phases
			.stream()
			.map(phase -> {
				IntcodeComputerBuilder icBuilder = IntcodeComputerBuilder.newInstance()
				.setSequenceNumber(getSequenceNumber(phases, phase))
				.setName(getAmpName(phases, phase))
				.setProgram(program)
				.addInput(phase)
				.setConnectsTo(getConnectsTo(phases, phase, useFeedback));
				if (phases.indexOf(phase) == 0) {
					icBuilder.addInput(0);
				}
				return icBuilder;
			})
			.forEach(builder::addBuilder);
		return builder.build();
	}

	private Integer getSequenceNumber(List<Integer> phases, Integer phase) {
		return phases.indexOf(phase) + 1;
	}

	private String getAmpName(List<Integer> phases, Integer phase) {
		return String.format("amp%s", (Character.toString((char)(65 + phases.indexOf(phase)))));
	}
	
	private String getConnectsTo(List<Integer> phases, Integer phase, boolean useFeedback) {
		int connectsToPhaseIndex = phases.indexOf(phase) + 1;
		if (connectsToPhaseIndex < phases.size()) {
			return getAmpName(phases, phases.get(connectsToPhaseIndex));
		}
		if (useFeedback) {
			return getAmpName(phases, phases.get(0));
		}
		return "";
	}
	
	public String getPhaseSetting() {
		return phaseSetting;
	}

	public String getProgram() {
		return program;
	}

	public Integer calculateThrust() throws InterruptedException, ExecutionException {
		runner.start();
		CompletableFuture<IntcodeComputerResult> future = CompletableFuture.supplyAsync(() -> {
			try {
				return runner.doProcessing();
			} catch (InterruptedException e) {
				LOG.warn("{}", e);
			} catch (ExecutionException e) {
				LOG.warn("{}", e);
			}
			return IntcodeComputerResult.errorInstance();
		});
		IntcodeComputerResult result = future.get();
		return result.getOutput();
	}
}
