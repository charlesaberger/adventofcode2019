package thebergers.adventofcode2019.intcodecomputer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import thebergers.adventofcode2019.intcodecomputer.IntcodeComputer;

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
		IntcodeComputerRunner runner = IntcodeComputerRunnerBuilder
				.newInstance()
				.addBuilder(
					IntcodeComputerBuilder.newInstance()
					.setName("Simple IntcodeComputer")
					.setProgram(input))
				.build();
		IntcodeComputerResult result = runner.doProcessing();
		assertThat(result.getResult()).as("Check output").isEqualTo(output);
		assertThat(result.isTerminated()).as("Is Terminated").isTrue();
	}
	
	@DisplayName("Test Intcode computer input opcode")
	@ParameterizedTest(name = "{index}: program => {0}, input => {1}, output => {2}")
	@CsvSource({
		"'3,1,101,1,1,0,99',4,'5,4,101,1,1,0,99'"
	})
	public void testInput(String program, Long input, String expected) {
		CompletableFuture.supplyAsync(() -> {
			IntcodeComputer ic = new IntcodeComputer(program);
			ic.addInput(input);
			return ic.processOpcodes();
		})
		.thenAccept(result -> 
			assertThat(result.getResult()).as("Check output").isEqualTo(expected)
		);
	}
	
	@DisplayName("Test Intcode computer input opcode")
	@ParameterizedTest(name = "{index}: program => {0}, input => {1}, output => {2}")
	@CsvSource({
		"'3,0,4,0,99',1,'1,0,4,0,99'"
	})
	public void testOutput(String program, Long input, String expected) {
		CompletableFuture.supplyAsync(() -> {
			IntcodeComputer ic = new IntcodeComputer(program);
			ic.addInput(input);
			return ic.processOpcodes();
		}).thenAccept(result -> {
			assertThat(result.getResult()).as("Check result").isEqualTo(result);
			assertThat(result.getOutput()).as("Check output").isEqualTo(input);
		});
	}
	
	@DisplayName("Find noun and verb that comprise a result")
	@Test
	public void testGetNounAndVerb() throws Exception {
		String program = "0,12,2,0,99";
		CompletableFuture.supplyAsync(() -> {
			IntcodeComputer ic = new IntcodeComputer(program);
			return ic.processOpcodes();
		}).thenAccept(result -> {
			assertThat(result.getNounAndVerb()).as("Check noun and verb").isEqualTo("1202");
		});
	}
	
	@DisplayName("Test jump-if-true")
	@ParameterizedTest(name = "{index}: program => {0}, result => {1}")
	@CsvSource({
		"'1005,9,6,4,10,99,4,11,99,0,-1,10',-1",
		"'1005,9,6,4,10,99,4,11,99,1,-1,10',10"
	})
	public void testJumpIfTrue(String program, Integer expectedResult) {
		CompletableFuture.supplyAsync(() -> {
			IntcodeComputer ic = new IntcodeComputer(program);
			return ic.processOpcodes();
		}).thenAccept(result -> assertThat(result.getOutput()).as("Check result").isEqualTo(expectedResult));
	}
	
	@DisplayName("Test jump-if-false")
	@ParameterizedTest(name = "{index}: program => {0}, result => {1}")
	@CsvSource({
		"'6,9,12,4,10,99,4,11,99,0,10,-1,6',-1",
		"'6,9,12,4,10,99,4,11,99,1,10,-1,6',10"
	})
	public void testJumpIfFalse(String program, Integer expectedResult) {
		CompletableFuture.supplyAsync(() -> {
			IntcodeComputer ic = new IntcodeComputer(program);
			return ic.processOpcodes();
		}).thenAccept(result -> assertThat(result.getOutput()).as("Check result").isEqualTo(expectedResult));
	}
	
	@DisplayName("Test jumps with input")
	@ParameterizedTest(name = "{index}: program => {0}, input => {1}, result => {2}")
	@CsvSource({
		"'3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9',0,0",
		"'3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9',10,1",
		"'3,3,1105,-1,9,1101,0,0,12,4,12,99,1',0,0",
		"'3,3,1105,-1,9,1101,0,0,12,4,12,99,1',10,1"
	})
	public void testJumpsWithInput(String program, Long input, Integer expectedResult) {
		CompletableFuture.supplyAsync(() -> {
			IntcodeComputer ic = new IntcodeComputer(program);
			ic.addInput(input);
			return ic.processOpcodes();
		}).thenAccept(result -> assertThat(result.getOutput()).as("Check result").isEqualTo(expectedResult));
	}
	
	@DisplayName("Test is less than")
	@ParameterizedTest(name = "{index}: program => {0}, result => {1}")
	@CsvSource({
		"'7,7,8,10,4,10,99,0,1,5,-1',1",
		"'7,7,8,10,4,10,99,2,1,5,-1',0"
	})
	public void testIsLessThan(String program, Integer expectedResult) {
		CompletableFuture.supplyAsync(() -> {
			IntcodeComputer ic = new IntcodeComputer(program);
			return ic.processOpcodes();
		}).thenAccept(result -> assertThat(result.getOutput()).as("Check result").isEqualTo(expectedResult));
	}
	
	@DisplayName("Test equals")
	@ParameterizedTest(name = "{index}: program => {0}, result => {1}")
	@CsvSource({
		"'8,7,8,9,4,9,99,50,50,-1',1",
		"'8,7,8,9,4,9,99,49,50,-1',0",
		"'8,7,8,9,4,9,99,51,50,-1',0"
	})
	public void testEquals(String program, Integer expectedResult) {
		CompletableFuture.supplyAsync(() -> {
			IntcodeComputer ic = new IntcodeComputer(program);
			return ic.processOpcodes();
		}).thenAccept(result -> assertThat(result.getOutput()).as("Check result").isEqualTo(expectedResult));
	}
	
	@DisplayName("Test conditional opcodes")
	@ParameterizedTest(name = "{index}: program => {0}, input => {1}, output => {2}")
	@CsvSource({
		"'3,9,8,9,10,9,4,9,99,-1,8',4,0",
		"'3,9,8,9,10,9,4,9,99,-1,8',8,1",
		"'3,9,7,9,10,9,4,9,99,-1,8',7,1",
		"'3,9,7,9,10,9,4,9,99,-1,8',9,0",
		"'3,3,1108,-1,8,3,4,3,99',56,0",
		"'3,3,1108,-1,8,3,4,3,99',8,1",
		"'3,3,1107,-1,8,3,4,3,99',8,0",
		"'3,3,1107,-1,8,3,4,3,99',7,1"
	})
	public void testConditionals(String program, Long input, Integer expected) {
		CompletableFuture.supplyAsync(() -> {
			IntcodeComputer ic = new IntcodeComputer(program);
			ic.addInput(input);
			return ic.processOpcodes();
		}).thenAccept(result -> assertThat(result.getOutput()).as("Check output").isEqualTo(expected));
	}
	
	@DisplayName("Test Diagnostic with input")
	@ParameterizedTest(name = "{index}: program => {0}, input => {1}, output => {2}")
	@CsvSource({
		"'3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99',7,999",
		"'3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99',8,1000",
		"'3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99',9,1001"
	})
	public void testDiagnosticWithInput(String program, Long input, Integer expected) {
		CompletableFuture.supplyAsync(() -> {
			IntcodeComputer ic = new IntcodeComputer(program);
			ic.addInput(input);
			return ic.processOpcodes();
		}).thenAccept(result -> assertThat(result.getOutput()).as("Check output").isEqualTo(expected));
	}
	
	@DisplayName("Tests from Reddit")
	@ParameterizedTest(name = "{index}: program => {0}, result => {1}")
	@CsvSource({
		"'1,0,3,3,1005,2,10,5,1,0,4,1,99',0",
		"'101,-1,7,7,4,7,1105,11,0,99',0"
	})
	public void testReddit(String program, Integer expectedResult) {
		CompletableFuture.supplyAsync(() -> {
			IntcodeComputer ic = new IntcodeComputer(program);
			return ic.processOpcodes();
		}).thenAccept(result -> assertThat(result.getOutput()).as("Check result").isEqualTo(expectedResult));
	}
	
	@DisplayName("Invalid opcode error")
	@ParameterizedTest(name = "{index}: program => {0}, result => {1}")
	@CsvSource({
		"'1101,9,0,0,105,1,0,1998,819,998',-1"
	})
	public void testInvalidOpcode(String program, Integer expectedResult) {
		IntcodeComputer ic = new IntcodeComputer(program);
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
			ic.processOpcodes();
		});
	}
	
	@Test
	public void testQueuedInput() {
		CompletableFuture.supplyAsync(() -> {
			String program = "3,11,3,12,1,11,12,13,4,13,99,-1,-1,-1";
			IntcodeComputer ic = new IntcodeComputer(program);
			ic.addInput(1L);
			ic.addInput(2L);
			return ic.processOpcodes();
		}).thenAccept(result -> {
			assertThat(result.getOutput()).as("1 + 2 = 3").isEqualTo(3);
			assertThat(result.isTerminated()).as("Is terminated?").isTrue();
		});
	}
	
	@DisplayName("Test save and exit")
	@Test
	public void testSaveExit() {
		CompletableFuture.supplyAsync(() -> {
		String program = "1101,2,2,11,4,11,1101,3,3,11,99,-1";
			IntcodeComputer ic = new IntcodeComputer(program);
			return ic.processOpcodes();
		}).thenAccept(result -> {
			assertThat(result.isTerminated()).as("Is Terminated?").isFalse();
			assertThat(result.getOutput()).as("Output").isEqualTo(4);
		});
	}
	
	@DisplayName("Test save, exit & resume")
	@Test
	public void testSaveExitResume() {
		String program = "1101,2,2,15,4,15,3,15,1001,15,3,15,4,15,99,-1";
		IntcodeComputer ic = new IntcodeComputer(program);
		CompletableFuture.supplyAsync(() -> ic.processOpcodes())
		.thenAccept(result -> {
			assertThat(result.getOutput()).as("Output").isEqualTo(4);
			assertThat(result.isTerminated()).as("Is Terminated").isFalse();
			ic.addInput(result.getOutput());
		});
		CompletableFuture.supplyAsync(() -> ic.processOpcodes())
		.thenAccept(result -> {
			assertThat(result.getOutput()).as("Output after resumed").isEqualTo(7);
			assertThat(result.isTerminated()).as("Is Terminated").isTrue();
		});
	}
	
	@DisplayName("Test Relative Parameters & memory extension (1)")
	@ParameterizedTest(name = "{index}: program: {0}, expected: {1}")
	@CsvSource({
		"'109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99','109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99'"
	})
	public void testDay09Pt1ex1(String program, String expected) throws Exception {
		IntcodeComputerRunner runner = IntcodeComputerRunnerBuilder.newInstance()
				.addBuilder(IntcodeComputerBuilder.newInstance()
						.setProgram(program))
				.build();
		IntcodeComputerResult result = runner.doProcessing();
		assertThat(result.getResult()).as("Check result").contains(expected);
	}
	
	@DisplayName("Test Relative Parameters & memory extension (2)")
	@Test
	public void testDay09Pt1ex2() throws Exception {
		String program = "1102,34915192,34915192,7,4,7,99,0";
		IntcodeComputerRunner runner = IntcodeComputerRunnerBuilder.newInstance()
				.addBuilder(IntcodeComputerBuilder.newInstance()
						.setProgram(program))
				.build();
		IntcodeComputerResult result = runner.doProcessing();
		assertThat(result.getOutput().toString().length()).as("Check output").isEqualTo(16);
	}
	
	@DisplayName("Test Relative Parameters & memory extension (3)")
	@Test
	public void testDay09Pt1ex3() throws Exception {
		String program = "104,1125899906842624,99";
		IntcodeComputerRunner runner = IntcodeComputerRunnerBuilder.newInstance()
				.addBuilder(IntcodeComputerBuilder.newInstance()
						.setProgram(program))
				.build();
		IntcodeComputerResult result = runner.doProcessing();
		assertThat(result.getOutput()).as("Check output").isEqualTo(1125899906842624L);
	}
	
	@DisplayName("Test Relative Parameter input")
	@ParameterizedTest(name = "{index}: program: {0}, input: {1}, expected: {2}")
	@CsvSource({
		/*"'203,3,104,0,99',50,50",
		"'109,5,203,0,104,0,99',27,27",*/
		"'209,9,21101,5,6,0,4,8,-1,8',0,11"
	})
	public void testRelativeParameterInput(String program, Long input, Long expected) throws Exception {
		//String program = "203,3,104,0,99";
		//Long input = 50L;
		IntcodeComputerRunner runner = IntcodeComputerRunnerBuilder.newInstance()
				.addBuilder(IntcodeComputerBuilder.newInstance()
						.setProgram(program)
						.setName("Test Relative Param Input")
						.addInput(input))
				.build();
		IntcodeComputerResult result = runner.doProcessing();
		assertThat(result.getOutput()).as("Check output").isEqualTo(expected);
	}
	
	@DisplayName("Tests for Output Parameters from Reddit")
	@ParameterizedTest(name = "{index}: program: {0}, input: {1}, expected: {2}")
	@CsvSource({
		/*"'109,-1,4,1,99',0,-1"
		,"'109,-1,104,1,99',0,1"
		,
		"'109,-1,204,1,99',0,109"
		,"'109,1,9,2,204,-6,99',0,204"
		,"'109,1,109,9,204,-6,99',0,204"
		,"'109,1,209,-1,204,-106,99',0,204"
		,"'109,1,3,3,204,2,99',2020,2020"
		,*/"'109,1,203,2,204,2,99',2020,2020"
	})
	public void testOutputParametersReddit(String program, Long input, Long expected) throws Exception {
		IntcodeComputerRunner runner = IntcodeComputerRunnerBuilder.newInstance()
				.addBuilder(IntcodeComputerBuilder.newInstance()
						.setProgram(program)
						.setName("Test Relative Param Input")
						.addInput(input))
				.build();
		IntcodeComputerResult result = runner.doProcessing();
		assertThat(result.getOutput()).as("Check output").isEqualTo(expected);
	}
}
