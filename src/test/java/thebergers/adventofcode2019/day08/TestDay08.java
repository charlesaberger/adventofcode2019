package thebergers.adventofcode2019.day08;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import thebergers.adventofcode2019.imageprocessor.ImageData;
import thebergers.adventofcode2019.imageprocessor.ImageLayer;
import thebergers.adventofcode2019.imageprocessor.ImageProcessor;
import thebergers.adventofcode2019.imageprocessor.ImageRow;

public class TestDay08 {

	@DisplayName("Test image decoder")
	@Test
	public void testImageDecoder() {
		String imageDataStr = "123456789012";
		Integer width = 3;
		Integer height = 2;
		ImageData imageData = ImageProcessor.decodeImageData(imageDataStr, width, height);
		assertThat(imageData.getLayerCount()).as("Count layers").isEqualTo(2);
		ImageLayer imageLayer1 = imageData.getLayer(1);
		assertThat(imageLayer1.getHeight()).as("Height of layer 1").isEqualTo(height);
		assertThat(imageLayer1.getWidth()).as("Width of layer").isEqualTo(width);
		ImageRow imageLayer1Row1 = imageLayer1.getRow(1);
		assertThat(imageLayer1Row1.getPixelsString()).as("Layer 1 Row 1 data").isEqualTo("123");
		ImageRow imageLayer1Row2 = imageLayer1.getRow(2);
		assertThat(imageLayer1Row2.getPixelsString()).as("Layer 1 Row 2 data").isEqualTo("456");
		ImageLayer imageLayer2 = imageData.getLayer(2);
		assertThat(imageLayer2.getHeight()).as("Height of layer 2").isEqualTo(height);
		assertThat(imageLayer2.getWidth()).as("Width of layer").isEqualTo(width);
		ImageRow imageLayer2Row1 = imageLayer2.getRow(1);
		assertThat(imageLayer2Row1.getPixelsString()).as("Layer 2 Row 1 data").isEqualTo("789");
		ImageRow imageLayer2Row2 = imageLayer2.getRow(2);
		assertThat(imageLayer2Row2.getPixelsString()).as("Layer 2 Row 2 data").isEqualTo("012");
	}
}
