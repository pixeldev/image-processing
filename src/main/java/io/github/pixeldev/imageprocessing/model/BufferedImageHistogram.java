package io.github.pixeldev.imageprocessing.model;

import java.util.Arrays;

public record BufferedImageHistogram(int[] histogram, int[] cumulativeHistogram, double[] normalizedHistogram, double[] cumulativeNormalizedHistogram, double mean, double variance, double skewness, double energy, double entropy) {
  @Override
  public String toString() {
    return "BufferedImageHistogram{" +
      "histogram=" + Arrays.toString(histogram) +
      ", normalizedHistogram=" + Arrays.toString(normalizedHistogram) +
      ", mean=" + mean +
      ", variance=" + variance +
      ", skewness=" + skewness +
      ", energy=" + energy +
      ", entropy=" + entropy +
      '}';
  }
}
