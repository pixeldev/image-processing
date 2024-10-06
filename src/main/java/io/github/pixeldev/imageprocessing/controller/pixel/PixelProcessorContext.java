package io.github.pixeldev.imageprocessing.controller.pixel;

import io.github.pixeldev.imageprocessing.model.BufferedImageSettings;

public class PixelProcessorContext {
  private final int x;
  private final int y;
  private final BufferedImageSettings settings;
  private int value;

  public PixelProcessorContext(final int x, final int y, final int value, final BufferedImageSettings settings) {
    this.x = x;
    this.y = y;
    this.settings = settings;
    this.value = value;
  }

  public int getX() {
    return this.x;
  }

  public int getY() {
    return this.y;
  }

  public BufferedImageSettings getSettings() {
    return this.settings;
  }

  public int getValue() {
    return this.value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  public int getRed() {
    return (this.value >> 16) & 0xFF;
  }

  public int getGreen() {
    return (this.value >> 8) & 0xFF;
  }

  public int getBlue() {
    return this.value & 0xFF;
  }
}
