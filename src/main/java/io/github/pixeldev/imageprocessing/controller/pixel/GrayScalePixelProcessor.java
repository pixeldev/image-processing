package io.github.pixeldev.imageprocessing.controller.pixel;

public enum GrayScalePixelProcessor implements PixelProcessor {
  INSTANCE;

  @Override
  public int process(final PixelProcessorContext context) {
    if (!context.getSettings().isMonochrome()) {
      return context.getValue();
    }

    final int rgb = context.getValue();
    final int red = (rgb >> 16) & 0xff;
    final int green = (rgb >> 8) & 0xff;
    final int blue = rgb & 0xff;
    final int gray = (red + green + blue) / 3;

    return (gray << 16) | (gray << 8) | gray;
  }
}
