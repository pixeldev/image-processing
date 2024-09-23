package io.github.pixeldev.pdipractica1.ui;

import io.github.pixeldev.pdipractica1.model.BufferedImageContainer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

public class ImagePanel extends JPanel {
  private final BufferedImageContainer bufferedImageContainer;
  private final JLabel imageLabel;
  private final MainFrame mainFrame;

  public ImagePanel(final BufferedImageContainer bufferedImageContainer, final MainFrame mainFrame) {
    this.bufferedImageContainer = bufferedImageContainer;
    this.mainFrame = mainFrame;

    this.imageLabel = new JLabel("Drop image here or click to load", SwingConstants.CENTER);
    this.imageLabel.setFont(new Font("Arial", Font.PLAIN, 24));

    super.setLayout(new BorderLayout());
    super.setBackground(Color.LIGHT_GRAY);
    super.add(this.imageLabel, BorderLayout.CENTER);

    this.enableDragAndDrop();
    this.enableClickToLoad();
  }

  private void enableDragAndDrop() {
    new DropTarget(this, new DropTargetListener() {
      @Override
      public void dragEnter(DropTargetDragEvent dtde) {
      }

      @Override
      public void dragOver(DropTargetDragEvent dtde) {
      }

      @Override
      public void dropActionChanged(DropTargetDragEvent dtde) {
      }

      @Override
      public void dragExit(DropTargetEvent dte) {
      }

      @Override
      public void drop(DropTargetDropEvent dtde) {
        try {
          dtde.acceptDrop(DnDConstants.ACTION_COPY);
          final List<File> droppedFiles = (List<File>) dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
          if (!droppedFiles.isEmpty()) {
            final File file = droppedFiles.get(0);
            ImagePanel.this.setImage(file);
          }
        } catch (final Exception ex) {
          JOptionPane.showMessageDialog(null, "Error loading image: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
      }
    });
  }

  public void openAndProcessImage() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif"));
    int returnValue = fileChooser.showOpenDialog(null);
    if (returnValue == JFileChooser.APPROVE_OPTION) {
      File selectedFile = fileChooser.getSelectedFile();
      this.setImage(selectedFile);
    }
  }

  public void updateImage() {
    this.imageLabel.setIcon(new ImageIcon(this.bufferedImageContainer.getModifiedImage()));
    this.imageLabel.setText("");
  }

  private void enableClickToLoad() {
    super.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        ImagePanel.this.openAndProcessImage();
      }
    });
  }

  public void setImage(final File file) {
    try {
      final BufferedImage bufferedImage = ImageIO.read(file);
      final int panelWidth = getWidth();
      final int panelHeight = getHeight();
      final int imageWidth = bufferedImage.getWidth();
      final int imageHeight = bufferedImage.getHeight();

      // Calculate the new dimensions while maintaining the aspect ratio
      final float aspectRatio = (float) imageWidth / imageHeight;
      int newWidth = panelWidth;
      int newHeight = (int) (panelWidth / aspectRatio);

      if (newHeight > panelHeight) {
        newHeight = panelHeight;
        newWidth = (int) (panelHeight * aspectRatio);
      }

      final Image resultingImage = bufferedImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
      final BufferedImage outputImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
      final Graphics2D g2d = outputImage.createGraphics();
      g2d.drawImage(resultingImage, 0, 0, null);
      g2d.dispose();
      this.bufferedImageContainer.setOriginalImage(outputImage);
      this.imageLabel.setIcon(new ImageIcon(resultingImage));
      this.imageLabel.setText("");

      // Notify MainFrame to add the ControlPanel
      this.mainFrame.addControlPanel();
    } catch (final Exception ex) {
      JOptionPane.showMessageDialog(null, "Error loading image: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
  }
}
