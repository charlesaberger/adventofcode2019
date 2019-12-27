package thebergers.adventofcode2019.imageprocessor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

public class ImageRow {

	private final Integer width;
	
	private List<Integer> pixels;

	public ImageRow(Integer width) {
		this.width = width;
	}

	public void addPixel(Integer pixel) {
		if (CollectionUtils.isEmpty(pixels)) {
			pixels = new ArrayList<>(width);
		}
		pixels.add(pixel);
	}

	public Integer getWidth() {
		return width;
	}
	
	public String getPixelsString() {
		return pixels.stream().map(pixel -> Integer.toString(pixel)).collect(Collectors.joining());
	}
	
	public long countDigit(Integer value) {
		return pixels.stream().filter(pixel -> pixel.equals(value)).count();
	}
}
