package io.github.pixeldev.pdipractica1.model;

import java.awt.image.BufferedImage;

public final class BufferedImageContainer {
  private final BufferedImageSettings settings = new BufferedImageSettings();

  private BufferedImage originalImage;
  private BufferedImage modifiedImage;

  public BufferedImage getOriginalImage() {
    return this.originalImage;
  }

  public void setOriginalImage(final BufferedImage originalImage) {
    this.originalImage = originalImage;
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
  }

  public void resetModifiedImage() {
    this.modifiedImage = BufferedImageHelper.copy(this.originalImage);
  }

  public BufferedImageSettings getSettings() {
    return this.settings;
  }
}
