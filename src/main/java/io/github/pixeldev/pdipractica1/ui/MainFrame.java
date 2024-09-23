package io.github.pixeldev.pdipractica1.ui;

import io.github.pixeldev.pdipractica1.controller.image.ImageProcessor;
import io.github.pixeldev.pdipractica1.model.BufferedImageContainer;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
  private final ImagePanel imagePanel;
  private final ControlPanel controlPanel;
  private final MenuBar menuBar;

  public MainFrame(final BufferedImageContainer bufferedImageContainer, final ImageProcessor imageProcessor) {
    super.setTitle("PDI | Práctica 1");
    super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    super.setLocationRelativeTo(null);

    this.imagePanel = new ImagePanel(bufferedImageContainer);
    this.controlPanel = new ControlPanel(bufferedImageContainer, this.imagePanel, imageProcessor);
    this.menuBar = new MenuBar(bufferedImageContainer, this.imagePanel);

    super.setJMenuBar(this.menuBar);
    super.setLayout(new BorderLayout());
    super.add(this.imagePanel, BorderLayout.CENTER);
    super.add(this.controlPanel, BorderLayout.EAST);

    super.setExtendedState(JFrame.MAXIMIZED_BOTH);
  }
}
