package thebergers.adventofcode2019.imageprocessor;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

public class ImageLayer {

	private final Integer height;
	
	private List<ImageRow> rows;

	public ImageLayer(Integer height) {
		this.height = height;
	}

	public void addRow(ImageRow row) {
		if (CollectionUtils.isEmpty(rows)) {
			rows = new ArrayList<>(height);
		}
		rows.add(row);
	}

	public Integer getHeight() {
		return height;
	}

	public Integer getWidth() {
		return rows.get(0).getWidth();
	}

	public ImageRow getRow(int i) {
		return rows.get(i - 1);
	}

	public long countDigit(Integer value) {
		return rows.stream()
				.mapToLong(row -> row.countDigit(value))
				.sum();
	}
}
