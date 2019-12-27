package thebergers.adventofcode2019.imageprocessor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

public class ImageRow {

	private final Integer width;
	
	private List<Pixel> pixels;

	public ImageRow(Integer width) {
		this.width = width;
	}
	public void addPixel(Integer pixel) {
		addPixel(Pixel.fromInteger(pixel));
	}

	public void addPixel(Pixel pixel) {
		if (CollectionUtils.isEmpty(pixels)) {
			pixels = new ArrayList<>(width);
		}
		pixels.add(pixel);
	}

	public Integer getWidth() {
		return width;
	}
	
	public String getPixelsString() {
		return pixels.stream().map(pixel -> Integer.toString(pixel.getId())).collect(Collectors.joining());
	}
	
	public long countDigit(Integer value) {
		return pixels.stream().filter(pixel -> pixel.getId().equals(value)).count();
	}
	public Pixel getPixel(Integer x) {
		return pixels.get(x - 1);
	}
}
