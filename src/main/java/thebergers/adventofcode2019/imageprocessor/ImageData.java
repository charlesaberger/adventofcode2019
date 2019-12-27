package thebergers.adventofcode2019.imageprocessor;

import java.util.LinkedList;
import java.util.List;

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
}
