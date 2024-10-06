package io.github.pixeldev.imageprocessing.controller.pixel;

import io.github.pixeldev.imageprocessing.model.ColorChannel;

public enum ColorChannelPixelProcessor implements PixelProcessor {
  INSTANCE;

  @Override
  public int process(final PixelProcessorContext context) {
    final ColorChannel colorChannel = context.getSettings().getChannel();

    return switch (colorChannel) {
      case RED -> (context.getRed() << 16);
      case GREEN -> (context.getGreen() << 8);
      case BLUE -> context.getBlue();
      default -> context.getValue();
    };
  }
}
