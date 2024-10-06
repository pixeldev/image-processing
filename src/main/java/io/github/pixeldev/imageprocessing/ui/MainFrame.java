package io.github.pixeldev.imageprocessing.ui;

import io.github.pixeldev.imageprocessing.controller.image.ImageProcessor;
import io.github.pixeldev.imageprocessing.model.BufferedImageContainer;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
  private final BufferedImageContainer bufferedImageContainer;
  private final ImagePanel imagePanel;
  private final ImageProcessor imageProcessor;
  private final MenuBar menuBar;
  private ControlPanel controlPanel;

  public MainFrame(final BufferedImageContainer bufferedImageContainer, final ImageProcessor imageProcessor) {
    super.setTitle("PDI | Práctica 1");
    super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    super.setLocationRelativeTo(null);

    this.bufferedImageContainer = bufferedImageContainer;
    this.imagePanel = new ImagePanel(bufferedImageContainer, this);
    this.imageProcessor = imageProcessor;
    this.menuBar = new MenuBar(bufferedImageContainer, this.imagePanel);

    super.setJMenuBar(this.menuBar);
    super.setLayout(new BorderLayout());
    super.add(this.imagePanel, BorderLayout.CENTER);

    super.setExtendedState(JFrame.MAXIMIZED_BOTH);
  }

  public void addControlPanel() {
    if (this.controlPanel == null) {
      this.controlPanel = new ControlPanel(this.bufferedImageContainer, this.imagePanel, this.imageProcessor);
      super.add(this.controlPanel, BorderLayout.EAST);
      super.revalidate();
      super.repaint();
    }
  }
}
