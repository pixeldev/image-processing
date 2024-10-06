package io.github.pixeldev.imageprocessing.model;

import java.awt.*;
import java.awt.image.BufferedImage;

public final class BufferedImageHelper {
  private BufferedImageHelper() {

  }

  public static BufferedImage copy(final BufferedImage source) {
    final BufferedImage copy = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
    final Graphics2D g2d = copy.createGraphics();
    g2d.drawImage(source, 0, 0, null);
    g2d.dispose();
    return copy;
  }
}
