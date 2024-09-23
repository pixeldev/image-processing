package io.github.pixeldev.pdipractica1;

import io.github.pixeldev.pdipractica1.controller.image.ImageProcessor;
import io.github.pixeldev.pdipractica1.controller.pixel.BrightnessPixelProcessor;
import io.github.pixeldev.pdipractica1.controller.pixel.ColorChannelPixelProcessor;
import io.github.pixeldev.pdipractica1.controller.pixel.ContrastPixelProcessor;
import io.github.pixeldev.pdipractica1.controller.pixel.GrayScalePixelProcessor;
import io.github.pixeldev.pdipractica1.model.BufferedImageContainer;
import io.github.pixeldev.pdipractica1.ui.MainFrame;

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
