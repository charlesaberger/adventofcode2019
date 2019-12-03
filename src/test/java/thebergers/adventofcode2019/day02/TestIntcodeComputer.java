package thebergers.adventofcode2019.day02;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class TestIntcodeComputer {

	@DisplayName("Test Intcode computer")
	@ParameterizedTest(name = "{index}, input => {0}, output => {1}")
	@CsvSource({
		"'1,0,0,0,99','2,0,0,0,99'",
		"'2,3,0,3,99','2,3,0,6,99'",
		"'2,4,4,5,99,0','2,4,4,5,99,9801'",
		"'1,1,1,4,99,5,6,0,99','30,1,1,4,2,5,6,0,99'",
		"'1,9,10,3,2,3,11,0,99,30,40,50','3500,9,10,70,2,3,11,0,99,30,40,50'"
	})
	public void test(String input, String output) throws Exception {
		IntcodeComputer ic = new IntcodeComputer(input);
		ic.processOpcodes();
		assertThat(ic.getResult()).as("Check output").isEqualTo(output);
	}
	
	@DisplayName("Find noun and verb that comprise a result")
	@Test
	public void testGetNounAndVerb() {
		String opcodes = "0,12,2,0,99";
		IntcodeComputer ic = new IntcodeComputer(opcodes);
		assertThat(ic.getNounAndVerb()).as("Check noun and verb").isEqualTo("1202");
	}
}
