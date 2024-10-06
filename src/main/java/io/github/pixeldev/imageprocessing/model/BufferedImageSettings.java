package io.github.pixeldev.imageprocessing.model;

public class BufferedImageSettings {
  private int brightness;
  private ColorChannel channel;
  private double contrast;
  private boolean monochrome;

  private int height;
  private int width;

  public BufferedImageSettings() {
    this.width = 0;
    this.height = 0;
    this.brightness = 0;
    this.contrast = 1.0;
    this.channel = ColorChannel.ORIGINAL;
  }

  public int getWidth() {
    return this.width;
  }

  public void setWidth(final int width) {
    this.width = width;
  }

  public int getHeight() {
    return this.height;
  }

  public void setHeight(final int height) {
    this.height = height;
  }

  public int getBrightness() {
    return this.brightness;
  }

  public void setBrightness(final int brightness) {
    this.brightness = brightness;
  }

  public double getContrast() {
    return this.contrast;
  }

  public void setContrast(final double contrast) {
    this.contrast = contrast;
  }

  public ColorChannel getChannel() {
    return this.channel;
  }

  public void setChannel(final ColorChannel channel) {
    this.channel = channel;
  }

  public boolean isMonochrome() {
    return this.monochrome;
  }

  public void setMonochrome(final boolean monochrome) {
    this.monochrome = monochrome;
  }
}
