package thebergers.adventofcode2019.dayone;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class TestFuelCounterUpper {

	private Module module;
	
	@DisplayName("Calculate fuel required for each module's mass")
	@ParameterizedTest(name = "{index} => mass= {0}, fuel= {1}")
	@CsvSource({
		"12, 2",
		"14, 2",
		"1969, 654",
		"100756, 33583"
	})
	void testCalculateFuel(int mass, int fuel) {
		module = new Module(mass);
		assertThat(module.calculateFuel()).as("Mass {}, Fuel {}", mass, fuel).isEqualTo(fuel);
	}
}
