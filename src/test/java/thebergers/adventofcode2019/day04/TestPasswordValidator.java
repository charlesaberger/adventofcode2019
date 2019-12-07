package thebergers.adventofcode2019.day04;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class TestPasswordValidator {

	private PasswordValidator pv;
	
	private static final Integer MIN_VALUE = 100001;
	
	private static final Integer MAX_VALUE = 299999;
	
	@BeforeEach
	public void setup() {
		pv = new PasswordValidator(MIN_VALUE, MAX_VALUE);
	}
	
	@DisplayName("Password Validator")
	@ParameterizedTest(name = "{index}: description = {0}, input = {1}, expectedResult = {2}")
	@CsvSource({
		"Below range,100000,false",
		"Beyond range, 333333,false",
		"Too Short,123,false",
		"Too Long,1234567,false",
		"No Increases,111111,true",
		"Contains a Decrease,223450,false",
		"No double digit,123789,false",
		"Valid1,122345,true",
		"Valid2,111123,true",
		"Valid3,133679,true",
		"Valid4,112233,true",
		"Group of 3 (1),123444,false",
		"Group of 3 (2),111233,false",
		"Group of 3 (3),122233,false",
		"Group of 3 (4),112223,false",
		"Valid5,111122,true",
		"Group of 4 (1),122223,true",
		"Group of 4 (2),123333,true",
		"Group of 5 (1),455555,false",
		"Group of 5 (2),111113,false",
		"Split group,112111,false"
	})
	public void testPasswordValidator(String description, Integer password, boolean expectedResult) {
		assertThat(pv.validatePassword(password)).as("Password %s", description).isEqualTo(expectedResult);
	}
}
