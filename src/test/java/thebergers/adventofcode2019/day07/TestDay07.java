package thebergers.adventofcode2019.day07;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
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
	
	
	@Disabled("Not ready for testing yet")
	@DisplayName("Calculate Max Thruster signal pt 2")
	@ParameterizedTest(name = "{index}: phase setting:{0}, result: {1}, program: {2}")
	@CsvSource({
		"'9,8,7,6,5',139629729,'3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5'"/*,
		"'9,7,8,5,6',18216,'3,52,1001,52,-5,52,3,53,1,52,56,54,1007,54,5,55,1005,55,26,1001,54,-5,54,1105,1,12,1,53,54,53,1008,54,0,55,1001,55,1,55,2,53,55,53,4,53,1001,56,-1,56,1005,56,6,99,0,0,0,0,10'"*/
	})
	public void testCalcMaxThrusterSignalPart2(String phaseSetting, Integer result, String program) {
		ThrusterSignalController tsc = ThrusterSignalControllerBuilder
				.newInstance()
				.setPhaseSettingRange(5, 9)
				.setProgram(program)
				.build();
		assertThat(tsc.calculateMaxThrustWithFeedback()).as("Calculate Max Thrust").isEqualTo(result);
	}
	
	@DisplayName("Generate phase settings")
	@ParameterizedTest(name ="{index}: rangeStart: {0}, rangeEnd: {1}")
	@CsvSource({
		"0,4",
		"5,9"
	})
	public void testPhaseSettings(Integer rangeStart, Integer rangeEnd) {
		ThrusterSignalControllerBuilder tscb = ThrusterSignalControllerBuilder
				.newInstance()
				.setPhaseSettingRange(rangeStart, rangeEnd)
				.setProgram("99");
		
		tscb.build();
		List<String> phaseSettings = tscb.getPhaseSettings();
		boolean duplicatesFound = phaseSettings.parallelStream()
				.collect(Collectors.groupingBy(p -> p))
				.values().stream()
				.filter(entry -> entry.size() > 1)
				.findFirst().isPresent();
		assertThat(duplicatesFound).isFalse();
	}
}
