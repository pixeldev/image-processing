package io.github.pixeldev.pdipractica1.controller.pixel;

public enum BrightnessPixelProcessor implements PixelProcessor {
  INSTANCE;

  @Override
  public int process(final PixelProcessorContext context) {
    final int red = context.getRed();
    final int green = context.getGreen();
    final int blue = context.getBlue();

    final int brightness = context.getSettings().getBrightness();

    final int newRed = Math.min(255, Math.max(0, red + brightness));
    final int newGreen = Math.min(255, Math.max(0, green + brightness));
    final int newBlue = Math.min(255, Math.max(0, blue + brightness));

    return (newRed << 16) | (newGreen << 8) | newBlue;
  }
}
