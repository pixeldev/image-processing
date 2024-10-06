package io.github.pixeldev.imageprocessing.ui;

import io.github.pixeldev.imageprocessing.model.BufferedImageContainer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MenuBar extends JMenuBar {

  public MenuBar(final BufferedImageContainer bufferedImageContainer, final ImagePanel imagePanel) {
    final JMenu fileMenu = new JMenu("Archivo");
    final JMenuItem loadItem = new JMenuItem("Cargar");
    final JMenuItem saveItem = new JMenuItem("Guardar");

    loadItem.addActionListener(actionEvent -> imagePanel.openAndProcessImage());

    saveItem.addActionListener(actionEvent -> {
      final JFileChooser fileChooser = new JFileChooser();
      final int returnValue = fileChooser.showSaveDialog(null);
      if (returnValue == JFileChooser.APPROVE_OPTION) {
        File selectedFile = fileChooser.getSelectedFile();
        if (!selectedFile.getName().endsWith(".jpg")) {
          selectedFile = new File(selectedFile.getAbsolutePath() + ".jpg");
        }
        final BufferedImage image = bufferedImageContainer.getModifiedImage();
        try {
          final boolean result = ImageIO.write(image, "JPG", selectedFile);
          if (result) {
            JOptionPane.showMessageDialog(null, "Imagen guardada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
          } else {
            JOptionPane.showMessageDialog(null, "Error al guardar la imagen: Formato no soportado", "Error", JOptionPane.ERROR_MESSAGE);
          }
        } catch (final IOException e) {
          JOptionPane.showMessageDialog(null, "Error al guardar la imagen", "Error", JOptionPane.ERROR_MESSAGE);
        }
      }
    });

    fileMenu.add(loadItem);
    fileMenu.add(saveItem);
    super.add(fileMenu);
  }
}
