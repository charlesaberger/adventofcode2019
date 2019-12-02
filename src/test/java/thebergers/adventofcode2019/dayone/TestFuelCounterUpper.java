package thebergers.adventofcode2019.dayone;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

public class TestFuelCounterUpper {
	
	@DisplayName("Calculate fuel required for each module's mass")
	@ParameterizedTest(name = "{index} => mass = {0}, fuel = {1}")
	@CsvSource({
		"12, 2",
		"14, 2",
		"1969, 654",
		"100756, 33583"
	})
	void testCalculateFuel(int mass, int fuel) {
		Module module = new Module(mass);
		assertThat(module.calculateFuel()).as("Mass {}, Fuel {}", mass, fuel).isEqualTo(fuel);
	}
	
	@DisplayName("Calculate additional fuel for module")
	@ParameterizedTest(name = "{index} => mass = {0}, extra fuel => {1}")
	@MethodSource("calculateAdditionalFuelArguments")
	void testCalculateAdditionalFuel(List<String> sourceModule, List<Module> expectedModules, int totalFuel) {
		FuelCounterUpper fcu = new FuelCounterUpper(sourceModule);
		assertThat(fcu.getModules()).as("Modules").isEqualTo(expectedModules);
		assertThat(fcu.calculateFuel()).as("Fuel").isEqualTo(totalFuel);
	}
	
	static Stream<Arguments> calculateAdditionalFuelArguments() {
		return Stream.of(
			arguments(generateStringList("12"), generateModuleList("12,2"), 2),
			arguments(generateStringList("1969"), generateModuleList("1969,654,216,70,21,5"), 966),
			arguments(generateStringList("100756"), generateModuleList("100756,33583,11192,3728,1240,411,135,43,12,2"), 50346)
		);
	}
	
	static List<Module> generateModuleList(String masses) {
		String[] additionalModulesArray = masses.split(",");
		List<Object> tempList = Arrays.asList(additionalModulesArray);
		List<Module> extraModulesExpected = new LinkedList<>();
		tempList.forEach(massObj -> { 
			extraModulesExpected.add(new Module(Integer.parseInt((String)massObj))); 
		});
		return extraModulesExpected;
	}
	
	static List<String> generateStringList(String value) {
		List<String> stringList = new LinkedList<>();
		stringList.add(value);
		return stringList;
	}
}
