package thebergers.adventofcode2019.day04;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class TestPasswordValidator {

	private PasswordValidator pv;
	
	private static final Integer MIN_VALUE = 1;
	
	private static final Integer MAX_VALUE = 299999;
	
	@BeforeEach
	public void setup() {
		pv = new PasswordValidator(MIN_VALUE, MAX_VALUE);
	}
	
	@DisplayName("Password Validator")
	@ParameterizedTest(name = "{index}: description = {0}, input = {1}, expectedResult = {2}")
	@CsvSource({
		"Below range,000000,false",
		"Beyond range, 333333,false",
		"Too Short,123,false",
		"Too Long,1234567,false",
		"No Increases,111111,true",
		"Contains a Decrease,223450,false",
		"No double digit,123789,false",
		"Valid1,122345,true",
		"Valid2,111123,true",
		"Valid3,133679,true"
	})
	public void testPasswordValidator(String description, Integer password, boolean expectedResult) {
		assertThat(pv.validatePassword(password)).as("Password %s", description).isEqualTo(expectedResult);
	}
}
