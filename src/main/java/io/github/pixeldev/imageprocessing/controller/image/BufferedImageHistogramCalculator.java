package io.github.pixeldev.imageprocessing.controller.image;

import io.github.pixeldev.imageprocessing.model.BufferedImageHistogram;
import io.github.pixeldev.imageprocessing.model.BufferedImageSettings;

import java.awt.image.BufferedImage;

public final class BufferedImageHistogramCalculator {
  private BufferedImageHistogramCalculator() {
    throw new UnsupportedOperationException("This class cannot be instantiated");
  }

  public static BufferedImageHistogram calculate(final BufferedImage bufferedImage, final BufferedImageSettings settings) {
    final int GRAY_LEVELS = 256;
    final int[] histogram = new int[GRAY_LEVELS];
    final int width = bufferedImage.getWidth();
    final int height = bufferedImage.getHeight();
    System.out.println("Width: " + width + ", Height: " + height);
    final int totalPixels = width * height;

    // Calcular el histograma
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        final int rgb = bufferedImage.getRGB(x, y);
        final int red = (rgb >> 16) & 0xFF;
        final int green = (rgb >> 8) & 0xFF;
        final int blue = rgb & 0xFF;
        final int index;
        switch (settings.getChannel()) {
          case RED -> index = red;
          case GREEN -> index = green;
          case BLUE -> index = blue;
          default -> index = (red + green + blue) / 3;
        }
        histogram[index]++;
      }
    }

    // Calcular el histograma normalizado y la media
    final double[] normalizedHistogram = new double[GRAY_LEVELS];
    double average = 0;
    for (int i = 0; i < GRAY_LEVELS; i++) {
      normalizedHistogram[i] = (double) histogram[i] / totalPixels;
      average += i * normalizedHistogram[i];
    }

    // Calcular el histograma acumulativo
    final int[] cumulativeHistogram = new int[GRAY_LEVELS];
    cumulativeHistogram[0] = histogram[0];
    for (int i = 1; i < GRAY_LEVELS; i++) {
      cumulativeHistogram[i] = cumulativeHistogram[i - 1] + histogram[i];
    }

    // Calcular el histograma normalizado acumulativo
    final double[] cumulativeNormalizedHistogram = new double[GRAY_LEVELS];
    cumulativeNormalizedHistogram[0] = normalizedHistogram[0];
    for (int i = 1; i < GRAY_LEVELS; i++) {
      cumulativeNormalizedHistogram[i] = cumulativeNormalizedHistogram[i - 1] + normalizedHistogram[i];
    }

    // Calcular varianza, asimetría, energía y entropía
    double variance = 0;
    double asymmetry = 0;
    double energy = 0;
    double entropy = 0;
    for (int i = 0; i < GRAY_LEVELS; i++) {
      final double probability = normalizedHistogram[i];
      final double deviation = i - average;
      variance += deviation * deviation * probability;
      asymmetry += deviation * deviation * deviation * probability;
      energy += probability * probability;
      if (probability > 0) {
        entropy -= probability * Math.log(probability);
      }
    }

    return new BufferedImageHistogram(histogram, cumulativeHistogram, normalizedHistogram, cumulativeNormalizedHistogram, average, variance, asymmetry, energy, entropy);
  }
}
