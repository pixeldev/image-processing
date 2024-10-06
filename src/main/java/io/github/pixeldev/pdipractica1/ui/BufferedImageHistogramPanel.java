package io.github.pixeldev.pdipractica1.ui;

import javax.swing.*;
import java.awt.*;

public class BufferedImageHistogramPanel extends JPanel {
  private final Color binColor;
  private final double[] histogram;
  private final String labelFormat;

  // Constructor que acepta int[]
  public BufferedImageHistogramPanel(final Color binColor, final int[] histogram) {
    this.binColor = binColor;
    this.histogram = new double[histogram.length];
    for (int i = 0; i < histogram.length; i++) {
      this.histogram[i] = histogram[i];
    }
    this.labelFormat = "%.2f";
  }

  // Constructor que acepta double[]
  public BufferedImageHistogramPanel(final Color binColor, final double[] histogram) {
    this.binColor = binColor;
    this.histogram = histogram;
    this.labelFormat = "%.4f";
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    drawHistogram(g);
  }

  private void drawHistogram(Graphics g) {
    int panelWidth = getWidth();
    int panelHeight = getHeight();

    // Márgenes para las etiquetas y los ejes
    int leftMargin = 100;
    int rightMargin = 20;
    int topMargin = 20;
    int bottomMargin = 50;

    int numBins = this.histogram.length; // Asumiendo 256 bins para píxeles de 0 a 255
    double binWidth = (double) (panelWidth - leftMargin - rightMargin) / numBins;

    // Encontrar la probabilidad máxima para escalar las barras
    double maxProbability = 0;
    for (double prob : this.histogram) {
      if (prob > maxProbability) {
        maxProbability = prob;
      }
    }

    // Configurar gráficos
    Graphics2D g2 = (Graphics2D) g;
    // Suavizado de líneas y texto
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    // Dibujar los ejes
    g2.setColor(Color.BLACK);
    // Eje X
    g2.drawLine(leftMargin, panelHeight - bottomMargin, panelWidth - rightMargin, panelHeight - bottomMargin);
    // Eje Y
    g2.drawLine(leftMargin, topMargin, leftMargin, panelHeight - bottomMargin);

    // Marcas y etiquetas del eje X
    int[] xTicks = {0, 64, 128, 192, 256};
    for (int xTick : xTicks) {
      int xPos = leftMargin + (int) (xTick * (panelWidth - leftMargin - rightMargin) / 256.0);
      // Marcas
      g2.drawLine(xPos, panelHeight - bottomMargin, xPos, panelHeight - bottomMargin + 5);
      // Etiquetas
      String label = Integer.toString(xTick);
      int labelWidth = g2.getFontMetrics().stringWidth(label);
      g2.drawString(label, xPos - labelWidth / 2, panelHeight - bottomMargin + 20);
    }

    // Marcas y etiquetas del eje Y
    int numYTicks = 5;
    for (int i = 0; i <= numYTicks; i++) {
      double probability = maxProbability * i / numYTicks;
      int yPos = panelHeight - bottomMargin - (int) (probability / maxProbability * (panelHeight - topMargin - bottomMargin));
      // Marcas
      g2.drawLine(leftMargin - 5, yPos, leftMargin, yPos);
      // Etiquetas
      String label = String.format(this.labelFormat, probability);
      int labelWidth = g2.getFontMetrics().stringWidth(label);
      g2.drawString(label, leftMargin - labelWidth - 10, yPos + 5);
    }

    // Dibujar el histograma
    for (int i = 0; i < numBins; i++) {
      double binHeightRatio = this.histogram[i] / maxProbability;
      int binHeight = (int) (binHeightRatio * (panelHeight - topMargin - bottomMargin));

      int x = leftMargin + (int) (i * binWidth);
      int y = panelHeight - bottomMargin - binHeight;

      g2.setColor(this.binColor);
      g2.fillRect(x, y, (int) Math.ceil(binWidth), binHeight);
    }
  }
}
