package io.github.pixeldev.imageprocessing.ui;

import io.github.pixeldev.imageprocessing.controller.image.ImageProcessor;
import io.github.pixeldev.imageprocessing.model.BufferedImageContainer;
import io.github.pixeldev.imageprocessing.model.BufferedImageHistogram;
import io.github.pixeldev.imageprocessing.model.ColorChannel;

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

    final String[] options = {"Brillo/Saturación", "Selección de canal", "Histograma"};
    for (final String option : options) {
      final JButton button = new JButton(option);
      button.addActionListener(new ButtonClickListener(bufferedImageContainer, imagePanel, imageProcessor));
      super.add(button, gbc);
    }
  }

  private record ButtonClickListener(BufferedImageContainer bufferedImageContainer, ImagePanel imagePanel, ImageProcessor imageProcessor) implements ActionListener {

    @Override
    public void actionPerformed(final ActionEvent e) {
      final String command = e.getActionCommand();
      switch (command) {
        case "Brillo/Saturación" -> this.showBrightnessSaturationDialog();
        case "Selección de canal" -> this.showChannelSelectionDialog();
        case "Histograma" -> this.showHistogramDialog();
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
      channelBox.setSelectedIndex(this.bufferedImageContainer.getSettings().getChannel().getValue());
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
      monochromeCheckBox.setSelected(this.bufferedImageContainer.getSettings().isMonochrome());
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

    private void showHistogramDialog() {
      final JDialog dialog = new JDialog((Frame) null, "Histograma", true);
      dialog.setLayout(new BorderLayout());

      final JPanel mainPanel = new JPanel();
      mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
      mainPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
      mainPanel.setAlignmentY(Component.CENTER_ALIGNMENT);

      final Color color = this.bufferedImageContainer.getSettings().getChannel().getColor();
      final BufferedImageHistogram histogram = this.bufferedImageContainer.getModifiedImageHistogram();

      // Panel para los histogramas organizados en una cuadrícula de 2x2
      JPanel histogramsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
      histogramsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

      // Primer histograma con título
      JPanel histogramPanel1 = new JPanel();
      histogramPanel1.setLayout(new BoxLayout(histogramPanel1, BoxLayout.Y_AXIS));
      JLabel title1 = new JLabel("Histograma");
      title1.setAlignmentX(Component.CENTER_ALIGNMENT);
      histogramPanel1.add(title1);
      histogramPanel1.add(new BufferedImageHistogramPanel(color, histogram.histogram()));

      // Segundo histograma con título
      JPanel histogramPanel2 = new JPanel();
      histogramPanel2.setLayout(new BoxLayout(histogramPanel2, BoxLayout.Y_AXIS));
      JLabel title2 = new JLabel("Histograma Acumulado");
      title2.setAlignmentX(Component.CENTER_ALIGNMENT);
      histogramPanel2.add(title2);
      histogramPanel2.add(new BufferedImageHistogramPanel(color, histogram.cumulativeHistogram()));

      // Tercer histograma con título
      JPanel histogramPanel3 = new JPanel();
      histogramPanel3.setLayout(new BoxLayout(histogramPanel3, BoxLayout.Y_AXIS));
      JLabel title3 = new JLabel("Histograma Normalizado");
      title3.setAlignmentX(Component.CENTER_ALIGNMENT);
      histogramPanel3.add(title3);
      histogramPanel3.add(new BufferedImageHistogramPanel(color, histogram.normalizedHistogram()));

      // Cuarto histograma con título
      JPanel histogramPanel4 = new JPanel();
      histogramPanel4.setLayout(new BoxLayout(histogramPanel4, BoxLayout.Y_AXIS));
      JLabel title4 = new JLabel("Histograma Acumulado Normalizado");
      title4.setAlignmentX(Component.CENTER_ALIGNMENT);
      histogramPanel4.add(title4);
      histogramPanel4.add(new BufferedImageHistogramPanel(color, histogram.cumulativeNormalizedHistogram()));

      // Añadir los paneles de histogramas al panel de cuadrícula
      histogramsPanel.add(histogramPanel1);
      histogramsPanel.add(histogramPanel2);
      histogramsPanel.add(histogramPanel3);
      histogramsPanel.add(histogramPanel4);

      // Añadir el panel de histogramas al panel principal
      mainPanel.add(histogramsPanel);

      // Panel para mostrar la información estadística
      JPanel statsPanel = new JPanel();
      statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
      statsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
      statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

      // Calcular estadísticas
      double mean = histogram.mean();
      double variance = histogram.variance();
      double skewness = histogram.skewness();
      double energy = histogram.energy();
      double entropy = histogram.entropy();

      // Crear etiquetas para las estadísticas
      JLabel meanLabel = new JLabel("Media: " + String.format("%.4f", mean));
      JLabel varianceLabel = new JLabel("Varianza: " + String.format("%.4f", variance));
      JLabel skewnessLabel = new JLabel("Asimetría: " + String.format("%.4f", skewness));
      JLabel energyLabel = new JLabel("Energía: " + String.format("%.8f", energy));
      JLabel entropyLabel = new JLabel("Entropía: " + String.format("%.4f", entropy));

      // Alinear las etiquetas al centro
      meanLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
      varianceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
      skewnessLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
      energyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
      entropyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

      // Añadir las etiquetas al panel de estadísticas
      statsPanel.add(meanLabel);
      statsPanel.add(varianceLabel);
      statsPanel.add(skewnessLabel);
      statsPanel.add(energyLabel);
      statsPanel.add(entropyLabel);

      // Añadir el panel de estadísticas al panel principal
      mainPanel.add(statsPanel);

      // Añadir el panel principal al diálogo
      dialog.add(new JScrollPane(mainPanel), BorderLayout.CENTER);
      dialog.setSize(1000, 600);
      dialog.setLocationRelativeTo(null);
      dialog.setVisible(true);
    }
  }
}
