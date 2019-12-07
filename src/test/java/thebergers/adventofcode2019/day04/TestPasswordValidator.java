package thebergers.adventofcode2019.day04;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class TestPasswordValidator {

	private PasswordValidator pv;
	
	private static final String MIN_VALUE = "000001";
	
	private static final String MAX_VALUE = "299999";
	
	@BeforeEach
	public void setup() {
		pv = new PasswordValidator(String.format("%s-%s", MIN_VALUE, MAX_VALUE));
	}
	
	@DisplayName("Password Validator")
	@ParameterizedTest(name = "index= {}, input= {0}, expectedResult = {1}")
	@CsvSource({
		"000000,false",
		"333333,false",
		"123,false",
		"1234567,false",
		"111111,false",
		"223450,false",
		"123789,false",
		"122345,true",
		"111123,true",
		"135679,true"
	})
	public void testPasswordValidator(Integer password, boolean expectedResult) {
		assertThat(pv.validatePassword(password)).as("Password {}", password).isEqualTo(expectedResult);
	}
}
