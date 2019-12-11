package thebergers.adventofcode2019.day02;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class TestIntcodeComputer {

	@DisplayName("Test Intcode computer add & multiply")
	@ParameterizedTest(name = "{index}, program => {0}, output => {1}")
	@CsvSource({
		"'1,0,0,0,99','2,0,0,0,99'",
		"'2,3,0,3,99','2,3,0,6,99'",
		"'2,4,4,5,99,0','2,4,4,5,99,9801'",
		"'1,1,1,4,99,5,6,0,99','30,1,1,4,2,5,6,0,99'",
		"'1,9,10,3,2,3,11,0,99,30,40,50','3500,9,10,70,2,3,11,0,99,30,40,50'",
		"'1002,4,3,4,33','1002,4,3,4,99'",
		"'1101,100,-1,4,0','1101,100,-1,4,99'"
	})
	public void testAddAndMultiply(String input, String output) throws Exception {
		IntcodeComputer ic = new IntcodeComputer(input);
		ic.processOpcodes();
		assertThat(ic.getResult()).as("Check output").isEqualTo(output);
	}
	
	@DisplayName("Test Intcode computer input opcode")
	@ParameterizedTest(name = "{index}: program => {0}, input => {1}, output => {2}")
	@CsvSource({
		"'3,1,101,1,1,0,99',4,'5,4,101,1,1,0,99'"
	})
	public void testInput(String program, Integer input, String result) {
		IntcodeComputer ic = new IntcodeComputer(program);
		ic.processOpcodes(input);
		assertThat(ic.getResult()).as("Check output").isEqualTo(result);
	}
	
	@DisplayName("Test Intcode computer input opcode")
	@ParameterizedTest(name = "{index}: program => {0}, input => {1}, output => {2}")
	@CsvSource({
		"'3,0,4,0,99',1,'1,0,4,0,99'"
	})
	public void testOutput(String program, Integer input, String result) {
		IntcodeComputer ic = new IntcodeComputer(program);
		ic.enableTestMode();
		ic.processOpcodes(input);
		assertThat(ic.getResult()).as("Check result").isEqualTo(result);
		assertThat(ic.getOutput()).as("Check output").isEqualTo(input);
	}
	
	@DisplayName("Find noun and verb that comprise a result")
	@Test
	public void testGetNounAndVerb() {
		String opcodes = "0,12,2,0,99";
		IntcodeComputer ic = new IntcodeComputer(opcodes);
		assertThat(ic.getNounAndVerb()).as("Check noun and verb").isEqualTo("1202");
	}
	
	@DisplayName("Test jump-if-true")
	@ParameterizedTest(name = "{index}: program => {0}, result => {1}")
	@CsvSource({
		"'5,9,6,4,10,99,4,11,99,0,-1,10',-1",
		"'5,9,6,4,10,99,4,11,99,1,-1,10',10"
	})
	public void testJumpIfTrue(String program, Integer expectedResult) {
		IntcodeComputer ic = new IntcodeComputer(program);
		ic.enableTestMode();
		ic.processOpcodes();
		assertThat(ic.getOutput()).as("Check result").isEqualTo(expectedResult);
	}
	
	@DisplayName("Test jump-if-false")
	@ParameterizedTest(name = "{index}: program => {0}, result => {1}")
	@CsvSource({
		"'6,0,6,4,9,99,4,10,99,10,-1',-1",
		"'6,0,6,4,9,99,4,10,99,10,-1',10"
	})
	public void testJumpIfFalse(String program, Integer expectedResult) {
		IntcodeComputer ic = new IntcodeComputer(program);
		ic.enableTestMode();
		ic.processOpcodes();
		assertThat(ic.getOutput()).as("Check result").isEqualTo(expectedResult);
	}
	
	@DisplayName("Test jumps")
	@ParameterizedTest(name = "{index}: program => {0}, result => {1}")
	@CsvSource({
		"'3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9',0,0",
		"'3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9',10,1",
		"'3,3,1105,-1,9,1101,0,0,12,4,12,99,1',0,0",
		"'3,3,1105,-1,9,1101,0,0,12,4,12,99,1',10,1"
	})
	public void testJumpsWithInput(String program, Integer input, Integer expectedResult) {
		IntcodeComputer ic = new IntcodeComputer(program);
		ic.enableTestMode();
		ic.processOpcodes(input);
		assertThat(ic.getOutput()).as("Check result").isEqualTo(expectedResult);
	}
	
	@DisplayName("Test is less than")
	@ParameterizedTest(name = "{index}: program => {0}, result => {1}")
	@CsvSource({
		"'7,0,1,5,4,-1,99',1",
		"'7,2,1,5,4,-1,99',0"
	})
	public void testIsLessThan(String program, Integer expectedResult) {
		IntcodeComputer ic = new IntcodeComputer(program);
		ic.enableTestMode();
		ic.processOpcodes();
		assertThat(ic.getOutput()).as("Check result").isEqualTo(expectedResult);
	}
	
	@DisplayName("Test equals")
	@ParameterizedTest(name = "{index}: program => {0}, result => {1}")
	@CsvSource({
		"'8,50,50,5,4,-1,99',1",
		"'8,49,50,5,4,-1,99',0",
		"'8,51,50,5,4,-1,99',0"
	})
	public void testEquals(String program, Integer expectedResult) {
		IntcodeComputer ic = new IntcodeComputer(program);
		ic.enableTestMode();
		ic.processOpcodes();
		assertThat(ic.getOutput()).as("Check result").isEqualTo(expectedResult);
	}
	
	

	@DisplayName("Test conditional opcodes")
	@ParameterizedTest(name = "{index}: program => {0}, input => {1}, result => {2}")
	@CsvSource({
		"'3,9,8,9,10,9,4,9,99,-1,8',4,0",
		"'3,9,8,9,10,9,4,9,99,-1,8',8,1",
		"'3,9,7,9,10,9,4,9,99,-1,8',7,0",
		"'3,9,7,9,10,9,4,9,99,-1,8',9,1",
		"'3,3,1108,-1,8,3,4,3,99',56,0",
		"'3,3,1108,-1,8,3,4,3,99',8,1",
		"'3,3,1107,-1,8,3,4,3,99',8,0",
		"'3,3,1107,-1,8,3,4,3,99',7,1"
	})
	public void testConditionals(String program, Integer input, String result) {
		IntcodeComputer ic = new IntcodeComputer(program);
		ic.enableTestMode();
		ic.processOpcodes(input);
		assertThat(ic.getResult()).as("Check output").isEqualTo(result);
	}	
}
