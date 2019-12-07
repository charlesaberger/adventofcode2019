package thebergers.adventofcode2019.day03;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import thebergers.adventofcode2019.utils.Utils;

public class Day3Part1 {

	public static void main(String[] args) throws IOException {
		String fileName = "./src/main/resources/daythree/input.txt";
		List<String> input = Utils.getDataFromFile(fileName);
		ControlPanel controlPanel = new ControlPanel(input);
		Point point = controlPanel.findClosestIntersection();
		int distance = point.distanceFromOrigin();
		Optional<Integer> fewestSteps = controlPanel.getMinimumNumSteps();
		Integer fewestStepsInt = fewestSteps.get();
		System.out.format("distance=%d, fewestSteps=%d", distance, fewestStepsInt);
	}
}
