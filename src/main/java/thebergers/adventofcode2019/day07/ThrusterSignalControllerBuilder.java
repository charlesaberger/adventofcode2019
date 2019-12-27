package thebergers.adventofcode2019.day07;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;

public class ThrusterSignalControllerBuilder {
	
	private final List<String> phaseSettings = new ArrayList<>();
	
	private Integer rangeStart = -1;
	
	private Integer rangeEnd = -1;
	
	private String program;
	
	private boolean useFeedback = false;

	public ThrusterSignalControllerBuilder addPhaseSetting(String phaseSetting) {
		phaseSettings.add(phaseSetting);
		return this;
	}
	
	public List<String> getPhaseSettings() {
		return phaseSettings;
	}

	public ThrusterSignalControllerBuilder setProgram(String program) {
		this.program = program;
		return this;
	}
	
	public ThrusterSignalControllerBuilder setUseFeedback(boolean useFeedback) {
		this.useFeedback = useFeedback;
		return this;
	}

	public static ThrusterSignalControllerBuilder newInstance() {
		return new ThrusterSignalControllerBuilder();
	}

	public ThrusterSignalController build() {
		if (StringUtils.isEmpty(program)) {
			throw new IllegalArgumentException("Error building ThrusterSignalController: No program specified!");
		}
		ThrusterSignalController controller = new ThrusterSignalController();
		if (phaseSettings.isEmpty()) {
			generatePhaseSettings();
		}
		phaseSettings
		.stream()
		.map(phase ->  new ThrusterSignalCalculator(phase, program, useFeedback))
		.forEach(controller::addThrusterSignalCalculator);
		return controller;
	}

	private void generatePhaseSettings() {
		int[] intList = IntStream.range(rangeStart, rangeEnd + 1).toArray();
		for (Integer a : intList) {
			for (Integer b : intList) {
				if (b.equals(a)) continue;
				for (Integer c : intList) {
					if (c.equals(a) || c.equals(b)) continue;
					for (Integer d : intList) {
						if (d.equals(a) || d.equals(b) || d.equals(c)) continue;
						for (Integer e : intList) {
							if (e.equals(a) || e.equals(b) || e.equals(c) || e.equals(d)) continue;
							phaseSettings.add(String.format("%d,%d,%d,%d,%d", a, b, c, d, e));
						}
					}
				}
			}
		}
	}

	public ThrusterSignalControllerBuilder setPhaseSettingRange(Integer rangeStart, Integer rangeEnd) {
		if (rangeStart < 0) {
			throw new IllegalArgumentException("rangeStart must be greater or equal t0 0");
		}
		if (rangeEnd < 0) {
			throw new IllegalArgumentException("rangeEnd must be greater or equal to 0");
		}
		if (rangeEnd <= rangeStart) {
			throw new IllegalArgumentException("rangeEnd must be greater than rangeStart");
		}
		this.rangeStart = rangeStart;
		this.rangeEnd = rangeEnd;
		return this;
	}
}
