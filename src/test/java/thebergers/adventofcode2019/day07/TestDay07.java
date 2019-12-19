package thebergers.adventofcode2019.day07;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class TestDay07 {

	@DisplayName("Calculate Max Thruster signal")
	@ParameterizedTest(name = "{index}: phase setting:{0}, result: {1}, program: {2}")
	@CsvSource({
		"'4,3,2,1,0',43210,'3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0'",
		"'0,1,2,3,4',54321,'3,23,3,24,1002,24,10,24,1002,23,-1,23,101,5,23,23,1,24,23,23,4,23,99,0,0'",
		"'1,0,4,3,2',65210,'3,31,3,32,1002,32,10,32,1001,31,-2,31,1007,31,0,33,1002,33,7,33,1,33,31,31,1,32,31,31,4,31,99,0,0,0'"
	})
	public void testCalcMaxThrusterSignal(String phaseSetting, Integer result, String program) {
		ThrusterSignalController tsc = ThrusterSignalControllerBuilder
				.newInstance()
				.addPhaseSetting(phaseSetting)
				.setProgram(program)
				.build();
		assertThat(tsc.calculateMaxThrust()).as("Calculate Max Thrust").isEqualTo(result);
	}
	
	@Test
	public void testPhaseSettings() {
		ThrusterSignalControllerBuilder tscb = ThrusterSignalControllerBuilder
				.newInstance()
				.setProgram("99");
		
		ThrusterSignalController tsc = tscb.build();
		List<String> phaseSettings = tscb.getPhaseSettings();
		boolean duplicatesFound = phaseSettings.parallelStream()
				.collect(Collectors.groupingBy(p -> p))
				.values().stream()
				.filter(entry -> entry.size() > 1)
				.findFirst().isPresent();
		assertThat(duplicatesFound).isFalse();
	}
}
