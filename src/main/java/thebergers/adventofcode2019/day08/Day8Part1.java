package thebergers.adventofcode2019.day08;

import java.io.IOException;
import java.util.List;

import thebergers.adventofcode2019.imageprocessor.ImageData;
import thebergers.adventofcode2019.imageprocessor.ImageProcessor;
import thebergers.adventofcode2019.utils.Utils;

public class Day8Part1 {

	public static void main(String[] args) throws IOException {
		String fileName = "./src/main/resources/day08/input.txt";
		List<String> input = Utils.getDataFromFile(fileName);
		ImageData imageData = ImageProcessor.decodeImageData(input.get(0), 25, 6);
		System.out.format("checksum=%d%n", imageData.calculateChecksum());
		System.out.format("finalImage:%n%s", imageData.getFinalImageAsText());
	}
}
