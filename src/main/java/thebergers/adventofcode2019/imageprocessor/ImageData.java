package thebergers.adventofcode2019.imageprocessor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;

public class ImageData {

	private final Integer width;
	
	private final Integer height;
	
	private List<ImageLayer> layers;

	public ImageData(Integer width, Integer height) {
		this.height = height;
		this.width = width;
	}

	public void addLayer(ImageLayer layer) {
		if (CollectionUtils.isEmpty(layers)) {
			layers = new LinkedList<>();
		}
		layers.add(layer);
	}

	public Integer getLayerCount() {
		return layers.size();
	}

	public ImageLayer getLayer(int i) {
		return layers.get(i - 1);
	}

	public long calculateChecksum() {
		Map<ImageLayer, Long> zeroCount = new HashMap<>();
		layers.stream().forEach(layer -> zeroCount.put(layer, layer.countDigit(0)));
		Optional<Entry<ImageLayer, Long>> fewestZerosEntry = zeroCount
			.entrySet()
			.stream()
			.sorted((entry1, entry2) -> entry1.getValue().compareTo(entry2.getValue()))
			.findFirst();
		
		if (!fewestZerosEntry.isPresent()) {
			return -1L;
		}
		ImageLayer layer = fewestZerosEntry.get().getKey();
		long ones = layer.countDigit(1);
		long twos = layer.countDigit(2);
		return ones * twos;
	}

	public ImageLayer getFinalImage() {
		ImageLayer image = new ImageLayer(1);
		ImageRow row = new ImageRow(width);
		for (int y = 1; y <= height; y++) {
			for (int x = 1; x <= width; x++) {
				Pixel pixel = getVisiblePixel(getPixelsAtPoint(x, y));
				row.addPixel(pixel);
			}
			image.addRow(row);
			row = new ImageRow(width);
		}
		return image;
	}
	
	public String getFinalImageAsText() {
		return getFinalImage().toString().replace("0", " ");
	}
	
	private List<Pixel> getPixelsAtPoint(Integer x, Integer y) {
		return layers.stream()
				.map(layer -> layer.getPixelAtPoint(x, y))
				.collect(Collectors.toList());
	}
	
	private Pixel getVisiblePixel(List<Pixel> pixels) {
		Optional<Pixel> pixelOpt = pixels.stream()
				.filter(Pixel::isVisible)
				.findFirst();
		if (!pixelOpt.isPresent()) {
			return Pixel.TRANSPARENT;
		}
		return pixelOpt.get();
	}
}
