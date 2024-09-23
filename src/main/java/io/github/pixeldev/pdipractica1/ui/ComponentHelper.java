package io.github.pixeldev.pdipractica1.ui;

import javax.swing.*;
import java.awt.*;
import java.util.function.IntConsumer;

public final class ComponentHelper {
  private ComponentHelper() {

  }

  public static JSlider createSlider(final int min, final int max, final int value, final IntConsumer action) {
    final JSlider slider = new JSlider(min, max, value);
    final int tickSpacing = (max - min) / 5;
    slider.setMajorTickSpacing(tickSpacing);
    slider.setMinorTickSpacing(tickSpacing / 5);
    slider.setPaintTicks(true);
    slider.setPaintLabels(true);
    slider.addChangeListener(e -> {
      final JSlider source = (JSlider) e.getSource();
      if (!source.getValueIsAdjusting()) {
        action.accept(source.getValue());
      }
    });
    slider.setBackground(new Color(245, 245, 245));
    return slider;
  }
}
