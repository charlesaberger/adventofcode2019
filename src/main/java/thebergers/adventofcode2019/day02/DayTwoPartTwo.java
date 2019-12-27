package thebergers.adventofcode2019.day02;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import thebergers.adventofcode2019.intcodecomputer.IntcodeComputer;
import thebergers.adventofcode2019.intcodecomputer.IntcodeComputerResult;
import thebergers.adventofcode2019.intcodecomputer.UnknownOpcodeException;
import thebergers.adventofcode2019.utils.Utils;

public class DayTwoPartTwo {

	private static final Logger LOG = LoggerFactory.getLogger(DayTwoPartTwo.class);
	private static final Integer MIN_VALUE = 0;
	private static final Integer MAX_VALUE = 99;
	
	static String inputStr = "";
	
	private final Integer expectedResult;
	
	public static void main(String[] args) throws Exception {
		DayTwoPartTwo d2p2 = new DayTwoPartTwo(19690720);
		String result = d2p2.doProcessing();
		System.out.println(String.format("Result is %s", result));
	}
	
	public DayTwoPartTwo(Integer expectedResult) {
		this.expectedResult = expectedResult;
	}
	
	private String doProcessing() throws IOException, InterruptedException, ExecutionException {
		IntcodeComputer intcodeComputer = new IntcodeComputer(initialise());
		for (Integer noun = MIN_VALUE; noun <= MAX_VALUE; noun++) {
			for (Integer verb = MIN_VALUE; verb <= MAX_VALUE; verb++) {
				intcodeComputer.setCode(1, noun);
				intcodeComputer.setCode(2, verb);
				CompletableFuture<IntcodeComputerResult> future = CompletableFuture.supplyAsync(() -> {
					return intcodeComputer.processOpcodes();
				});
				IntcodeComputerResult result = future.get();
				Integer nounValue = result.getPosition(noun);
				Integer verbValue = result.getPosition(verb);
				Integer resultValue = result.getPosition(0);
				LOG.info("noun: {}, verb: {}, nounValue: {}, verbValue: {}, resultValue: {}",
						noun, verb, nounValue, verbValue, resultValue);
				if (resultValue.equals(expectedResult)) {
					return result.getNounAndVerb();
				}
				intcodeComputer.reset(initialise());
			}
		}
		return "No Result";
	}
	
	private static String initialise() throws IOException {
		if (inputStr.trim().length() == 0) {
			String fileName = "./src/main/resources/daytwo/input.txt";
			List<String> input = Utils.getDataFromFile(fileName);
			inputStr = input.get(0);
		}
		return inputStr;
	}

}
