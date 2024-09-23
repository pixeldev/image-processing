package io.github.pixeldev.pdipractica1.controller.image;

import io.github.pixeldev.pdipractica1.controller.pixel.PixelProcessor;
import io.github.pixeldev.pdipractica1.controller.pixel.PixelProcessorContext;
import io.github.pixeldev.pdipractica1.model.BufferedImageContainer;
import io.github.pixeldev.pdipractica1.model.BufferedImageSettings;

import java.awt.image.BufferedImage;
import java.util.List;

public final class ImageProcessor {
  private final List<PixelProcessor> pixelProcessors;

  public ImageProcessor(final List<PixelProcessor> pixelProcessors) {
    this.pixelProcessors = pixelProcessors;
  }

  public void processImage(final BufferedImageContainer bufferedImageContainer) {
    final BufferedImage bufferedImage = bufferedImageContainer.cloneOriginalImage();
    final BufferedImageSettings bufferedImageSettings = bufferedImageContainer.getSettings();
    for (int x = 0; x < bufferedImage.getWidth(); x++) {
      for (int y = 0; y < bufferedImage.getHeight(); y++) {
        int value = bufferedImage.getRGB(x, y);
        final PixelProcessorContext pixelProcessorContext = new PixelProcessorContext(x, y, value, bufferedImageSettings);

        for (final PixelProcessor pixelProcessor : this.pixelProcessors) {
          value = pixelProcessor.process(pixelProcessorContext);
          pixelProcessorContext.setValue(value);
        }

        bufferedImage.setRGB(x, y, value);
      }
    }
    bufferedImageContainer.setModifiedImage(bufferedImage);
  }
}
