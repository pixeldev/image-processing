package io.github.pixeldev.imageprocessing.model;

import io.github.pixeldev.imageprocessing.controller.image.BufferedImageHistogramCalculator;

import java.awt.image.BufferedImage;

public final class BufferedImageContainer {
  private final BufferedImageSettings settings = new BufferedImageSettings();

  private BufferedImage originalImage;
  private BufferedImage modifiedImage;
  private BufferedImageHistogram originalImageHistogram;
  private BufferedImageHistogram modifiedImageHistogram;

  public BufferedImageHistogram getOriginalImageHistogram() {
    return this.originalImageHistogram;
  }

  public BufferedImageHistogram getModifiedImageHistogram() {
    return this.modifiedImageHistogram;
  }

  public BufferedImage getOriginalImage() {
    return this.originalImage;
  }

  public void setOriginalImage(final BufferedImage originalImage) {
    this.originalImage = originalImage;
    this.originalImageHistogram = BufferedImageHistogramCalculator.calculate(originalImage, this.settings);
    this.resetModifiedImage();
    this.settings.setWidth(originalImage.getWidth());
    this.settings.setHeight(originalImage.getHeight());
  }

  public BufferedImage cloneOriginalImage() {
    return BufferedImageHelper.copy(this.originalImage);
  }

  public BufferedImage getModifiedImage() {
    return this.modifiedImage;
  }

  public void setModifiedImage(final BufferedImage modifiedImage) {
    this.modifiedImage = modifiedImage;
    this.modifiedImageHistogram = BufferedImageHistogramCalculator.calculate(modifiedImage, this.settings);
  }

  public void resetModifiedImage() {
    this.modifiedImage = BufferedImageHelper.copy(this.originalImage);
    this.modifiedImageHistogram = this.originalImageHistogram;
  }

  public BufferedImageSettings getSettings() {
    return this.settings;
  }
}
