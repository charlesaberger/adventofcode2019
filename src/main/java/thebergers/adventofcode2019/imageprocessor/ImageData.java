package thebergers.adventofcode2019.imageprocessor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import javax.swing.text.LayeredHighlighter;

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
}
