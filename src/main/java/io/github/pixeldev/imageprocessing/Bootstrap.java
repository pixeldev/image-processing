package io.github.pixeldev.imageprocessing;

import io.github.pixeldev.imageprocessing.controller.image.ImageProcessor;
import io.github.pixeldev.imageprocessing.controller.pixel.BrightnessPixelProcessor;
import io.github.pixeldev.imageprocessing.controller.pixel.ColorChannelPixelProcessor;
import io.github.pixeldev.imageprocessing.controller.pixel.ContrastPixelProcessor;
import io.github.pixeldev.imageprocessing.controller.pixel.GrayScalePixelProcessor;
import io.github.pixeldev.imageprocessing.model.BufferedImageContainer;
import io.github.pixeldev.imageprocessing.ui.MainFrame;

import javax.swing.*;
import java.util.List;

public final class Bootstrap {
  private Bootstrap() {
    throw new UnsupportedOperationException("This class cannot be instantiated");
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      final BufferedImageContainer bufferedImageContainer = new BufferedImageContainer();
      final ImageProcessor imageProcessor = new ImageProcessor(List.of(ColorChannelPixelProcessor.INSTANCE, GrayScalePixelProcessor.INSTANCE, BrightnessPixelProcessor.INSTANCE, ContrastPixelProcessor.INSTANCE));
      MainFrame gui = new MainFrame(bufferedImageContainer, imageProcessor);
      gui.setVisible(true);
    });
  }
}
