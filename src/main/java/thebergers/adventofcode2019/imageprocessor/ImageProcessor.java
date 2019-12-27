package thebergers.adventofcode2019.imageprocessor;

public class ImageProcessor {

	public static ImageData decodeImageData(String imageDataStr, Integer width, Integer height) {
		char[] imageChars = imageDataStr.toCharArray();
		ImageData imageData = new ImageData(width, height);
		ImageLayer layer = new ImageLayer(height);
		ImageRow row = new ImageRow(width);
		for (int i = 1; i <= imageChars.length; i++) {
			Integer pixel = Integer.parseInt(String.valueOf(imageChars[i - 1]));
			row.addPixel(pixel);
			if (i % width == 0) {
				layer.addRow(row);
				row = new ImageRow(width);
				if (i % (width * height) == 0) {
					imageData.addLayer(layer);
					layer = new ImageLayer(height);
				}
			}
		}
		return imageData;
	}

	private ImageProcessor() {
		
	}
}
