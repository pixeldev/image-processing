package io.github.pixeldev.pdipractica1.controller.pixel;

public enum ContrastPixelProcessor implements PixelProcessor {
  INSTANCE;

  @Override
  public int process(final PixelProcessorContext context) {
    final int red = context.getRed();
    final int green = context.getGreen();
    final int blue = context.getBlue();

    final double contrast = context.getSettings().getContrast();

    final int newRed = Math.min(255, Math.max(0, (int) ((red - 128) * contrast + 128)));
    final int newGreen = Math.min(255, Math.max(0, (int) ((green - 128) * contrast + 128)));
    final int newBlue = Math.min(255, Math.max(0, (int) ((blue - 128) * contrast + 128)));

    return (newRed << 16) | (newGreen << 8) | newBlue;
  }
}
