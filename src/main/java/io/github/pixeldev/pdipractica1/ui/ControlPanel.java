package io.github.pixeldev.pdipractica1.ui;

import io.github.pixeldev.pdipractica1.controller.image.ImageProcessor;
import io.github.pixeldev.pdipractica1.model.BufferedImageContainer;
import io.github.pixeldev.pdipractica1.model.ColorChannel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel {

  public ControlPanel(final BufferedImageContainer bufferedImageContainer, final ImagePanel imagePanel, final ImageProcessor imageProcessor) {
    super.setLayout(new GridBagLayout());
    final GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridx = 0;
    gbc.gridy = GridBagConstraints.RELATIVE;
    gbc.weightx = 1.0;
    gbc.insets = new Insets(5, 0, 5, 0);

    final String[] options = {"Brillo/Saturación", "Selección de canal"};
    for (final String option : options) {
      final JButton button = new JButton(option);
      button.addActionListener(new ButtonClickListener(bufferedImageContainer, imagePanel, imageProcessor));
      super.add(button, gbc);
    }
  }

  private static class ButtonClickListener implements ActionListener {
    private final BufferedImageContainer bufferedImageContainer;
    private final ImagePanel imagePanel;
    private final ImageProcessor imageProcessor;

    private ButtonClickListener(final BufferedImageContainer bufferedImageContainer, final ImagePanel imagePanel, final ImageProcessor imageProcessor) {
      this.bufferedImageContainer = bufferedImageContainer;
      this.imagePanel = imagePanel;
      this.imageProcessor = imageProcessor;
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
      final String command = e.getActionCommand();
      switch (command) {
        case "Brillo/Saturación":
          this.showBrightnessSaturationDialog();
          break;
        case "Selección de canal":
          this.showChannelSelectionDialog();
          break;
      }
    }

    private void showBrightnessSaturationDialog() {
      final JDialog dialog = new JDialog((Frame) null, "Brillo/Contraste", true);
      dialog.setLayout(new BorderLayout());

      final JPanel panel = new JPanel();
      panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
      panel.setAlignmentX(Component.CENTER_ALIGNMENT);
      panel.setAlignmentY(Component.CENTER_ALIGNMENT);

      final int brightnessValue = this.bufferedImageContainer.getSettings().getBrightness();

      final JLabel brightnessLabel = new JLabel("Brillo (" + brightnessValue + ")");
      brightnessLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

      final JSlider brightnessSlider = ComponentHelper.createSlider(
        -255,
        255,
        brightnessValue,
        value -> {
          brightnessLabel.setText("Brillo (" + value + ")");
          this.bufferedImageContainer.getSettings().setBrightness(value);
          this.imageProcessor.processImage(this.bufferedImageContainer);
          this.imagePanel.updateImage();
        });

      final int contrastValue = (int) (this.bufferedImageContainer.getSettings().getContrast() * 100);

      final JLabel contrastLabel = new JLabel("Contraste (" + contrastValue + ")");
      contrastLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

      final JSlider contrastSlider = ComponentHelper.createSlider(
        0,
        400,
        contrastValue,
        value -> {
          contrastLabel.setText("Contraste (" + value + ")");
          final double newContrastValue = value / 100.0;
          this.bufferedImageContainer.getSettings().setContrast(newContrastValue);
          this.imageProcessor.processImage(this.bufferedImageContainer);
          this.imagePanel.updateImage();
        });

      panel.add(Box.createRigidArea(new Dimension(0, 10))); // Add some space between components
      panel.add(brightnessLabel);
      panel.add(brightnessSlider);
      panel.add(Box.createRigidArea(new Dimension(0, 10))); // Add some space between components
      panel.add(contrastLabel);
      panel.add(contrastSlider);

      dialog.add(panel, BorderLayout.CENTER);
      dialog.setSize(500, 200);
      dialog.setLocationRelativeTo(null);
      dialog.setVisible(true);
    }

    private void showChannelSelectionDialog() {
      final JDialog dialog = new JDialog((Frame) null, "Selección de canal", true);
      dialog.setLayout(new FlowLayout());

      final String[] channels = {"Original", "Rojo", "Verde", "Azul"};
      final JComboBox<String> channelBox = new JComboBox<>(channels);
      channelBox.addActionListener(e -> {
        final JComboBox<String> source = (JComboBox<String>) e.getSource();
        final ColorChannel colorChannel = ColorChannel.fromValue(source.getSelectedIndex());
        this.bufferedImageContainer.getSettings().setChannel(colorChannel);
        this.imageProcessor.processImage(this.bufferedImageContainer);
        this.imagePanel.updateImage();
      });
      dialog.add(new JLabel("Selecciona un canal:"));
      dialog.add(channelBox);

      final JCheckBox monochromeCheckBox = new JCheckBox("Monocromático");
      monochromeCheckBox.addActionListener(e -> {
        final JCheckBox source = (JCheckBox) e.getSource();
        this.bufferedImageContainer.getSettings().setMonochrome(source.isSelected());
        this.imageProcessor.processImage(this.bufferedImageContainer);
        this.imagePanel.updateImage();
      });
      dialog.add(monochromeCheckBox);

      dialog.setSize(300, 100);
      dialog.setLocationRelativeTo(null);
      dialog.setVisible(true);
    }
  }
}
