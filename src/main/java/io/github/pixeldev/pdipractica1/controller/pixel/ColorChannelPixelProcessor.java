package io.github.pixeldev.pdipractica1.controller.pixel;

import io.github.pixeldev.pdipractica1.model.ColorChannel;

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
