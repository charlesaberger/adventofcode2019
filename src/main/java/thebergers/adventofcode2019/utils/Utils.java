package thebergers.adventofcode2019.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Utils {

	private Utils() {
		
	}
	
	public static List<String> getDataFromFile(String fileName) throws IOException {
		File file = new File(fileName);
		List<String> data = new LinkedList<>();
		try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
			while (true) {
				String record = reader.readLine();
				if (record == null) {
					break;
				}
				data.add(record);
			}
		}
		return data;
	}

}
