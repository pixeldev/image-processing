package io.github.pixeldev.pdipractica1;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;

public class ImageViewer extends JFrame {
    private final JLabel imageLabel;
    private BufferedImage originalImage;
    private final JSlider zoomSlider;
    private final JSlider brightnessSlider;
    private final JSlider contrastSlider;
    private final JComboBox<String> colorChannelBox;
    private final JCheckBox monochromeCheckBox;

    public ImageViewer() {
        super.setTitle("Image Viewer");
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setExtendedState(JFrame.MAXIMIZED_BOTH);
        super.setLocationRelativeTo(null);

        final JButton loadButton = new JButton("Load Image");
        loadButton.addActionListener(actionEvent -> this.loadImage());
        loadButton.setFont(new Font("Arial", Font.PLAIN, 16));
        loadButton.setBackground(new Color(30, 144, 255));
        loadButton.setForeground(Color.BLACK);
        loadButton.setFocusPainted(false);
        loadButton.setBorder(new CompoundBorder(
                new RoundedBorder(20),
                new EmptyBorder(5, 20, 5, 20)
        ));

        this.imageLabel = new JLabel();
        this.imageLabel.setHorizontalAlignment(JLabel.CENTER);
        this.imageLabel.setVerticalAlignment(JLabel.CENTER);

        this.zoomSlider = new JSlider(50, 200, 100);
        this.zoomSlider.setMajorTickSpacing(50);
        this.zoomSlider.setPaintTicks(true);
        this.zoomSlider.setPaintLabels(true);
        this.zoomSlider.addChangeListener(e -> this.updateZoom());
        this.zoomSlider.setBackground(new Color(245, 245, 245));

        this.brightnessSlider = new JSlider(-100, 100, 0);
        this.brightnessSlider.setMajorTickSpacing(50);
        this.brightnessSlider.setPaintTicks(true);
        this.brightnessSlider.setPaintLabels(true);
        this.brightnessSlider.addChangeListener(e -> this.updateZoom());
        this.brightnessSlider.setBackground(new Color(245, 245, 245));

        this.contrastSlider = new JSlider(-100, 100, 0);
        this.contrastSlider.setMajorTickSpacing(50);
        this.contrastSlider.setPaintTicks(true);
        this.contrastSlider.setPaintLabels(true);
        this.contrastSlider.addChangeListener(e -> this.updateZoom());
        this.contrastSlider.setBackground(new Color(245, 245, 245));

        this.colorChannelBox = new JComboBox<>(new String[]{"Original", "Red", "Green", "Blue"});
        this.colorChannelBox.addActionListener(e -> this.updateZoom());
        this.colorChannelBox.setFont(new Font("Arial", Font.PLAIN, 16));
        this.colorChannelBox.setBackground(new Color(245, 245, 245));
        this.colorChannelBox.setBorder(new EmptyBorder(5, 150, 5, 150));

        this.monochromeCheckBox = new JCheckBox("Monochrome");
        this.monochromeCheckBox.setBackground(new Color(245, 245, 245));
        this.monochromeCheckBox.addActionListener(e -> this.updateZoom());

        final JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBackground(new Color(245, 245, 245));
        controlPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        final JPanel loadButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        loadButtonPanel.setBackground(new Color(245, 245, 245));
        loadButtonPanel.add(loadButton);

        controlPanel.add(loadButtonPanel);
        controlPanel.add(Box.createVerticalStrut(10));
        controlPanel.add(this.zoomSlider);
        controlPanel.add(Box.createVerticalStrut(10));
        controlPanel.add(new JLabel("Brightness"));
        controlPanel.add(this.brightnessSlider);
        controlPanel.add(Box.createVerticalStrut(10));
        controlPanel.add(new JLabel("Contrast"));
        controlPanel.add(this.contrastSlider);
        controlPanel.add(Box.createVerticalStrut(10));
        controlPanel.add(this.colorChannelBox);
        controlPanel.add(Box.createVerticalStrut(10));
        controlPanel.add(this.monochromeCheckBox);

        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(controlPanel, BorderLayout.NORTH);
        mainPanel.add(this.imageLabel, BorderLayout.CENTER);

        super.add(mainPanel);
    }

    private void loadImage() {
        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("JPEG file", "jpg", "jpeg"));
        final int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            final File selectedFile = fileChooser.getSelectedFile();
            try {
                this.originalImage = ImageIO.read(selectedFile);
                final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                final int screenWidth = screenSize.width;
                final int screenHeight = screenSize.height;

                int imageWidth = this.originalImage.getWidth();
                int imageHeight = this.originalImage.getHeight();

                if (imageWidth > screenWidth || imageHeight > screenHeight) {
                    final double widthFactor = (double) screenWidth / imageWidth;
                    final double heightFactor = (double) screenHeight / imageHeight;
                    final double scaleFactor = Math.min(widthFactor, heightFactor) * 0.8;

                    imageWidth = (int) (imageWidth * scaleFactor);
                    imageHeight = (int) (imageHeight * scaleFactor);
                    this.originalImage = resizeImage(this.originalImage, imageWidth, imageHeight);
                }

                this.updateZoom();
            } catch (final IOException ex) {
                JOptionPane.showMessageDialog(this, "Error loading image: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private BufferedImage resizeImage(final BufferedImage originalImage, final int targetWidth, final int targetHeight) {
        final Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        final BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(resultingImage, 0, 0, null);
        g2d.dispose();
        return outputImage;
    }

    private void updateZoom() {
        if (this.originalImage != null) {
            final int zoomPercent = this.zoomSlider.getValue();
            final int newWidth = this.originalImage.getWidth() * zoomPercent / 100;
            final int newHeight = this.originalImage.getHeight() * zoomPercent / 100;
            final Image scaledImage = this.originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

            BufferedImage bufferedScaledImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
            final Graphics2D g2d = bufferedScaledImage.createGraphics();
            g2d.drawImage(scaledImage, 0, 0, null);
            g2d.dispose();

            final String selectedChannel = (String) this.colorChannelBox.getSelectedItem();
            if (selectedChannel != null && !selectedChannel.equals("Original")) {
                bufferedScaledImage = applyColorFilter(bufferedScaledImage, selectedChannel);
            }

            bufferedScaledImage = adjustBrightness(bufferedScaledImage, this.brightnessSlider.getValue());
            bufferedScaledImage = adjustContrast(bufferedScaledImage, this.contrastSlider.getValue());

            if (this.monochromeCheckBox.isSelected()) {
                bufferedScaledImage = applyGrayscaleFilter(bufferedScaledImage);
            }

            this.imageLabel.setIcon(new ImageIcon(bufferedScaledImage));
        }
    }

    private BufferedImage applyColorFilter(final BufferedImage image, final String channel) {
        final BufferedImage filteredImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                final int rgb = image.getRGB(x, y);
                final int newRgb = getNewRgb(channel, rgb);
                filteredImage.setRGB(x, y, newRgb);
            }
        }
        return filteredImage;
    }

    private static int getNewRgb(final String channel, final int rgb) {
        final int alpha = (rgb >> 24) & 0xff;
        final int red = (rgb >> 16) & 0xff;
        final int green = (rgb >> 8) & 0xff;
        final int blue = rgb & 0xff;

        return switch (channel) {
            case "Red" -> (alpha << 24) | (red << 16);
            case "Green" -> (alpha << 24) | (green << 8);
            case "Blue" -> (alpha << 24) | blue;
            default -> 0;
        };
    }

    private BufferedImage adjustBrightness(BufferedImage image, int brightness) {
        RescaleOp rescaleOp = new RescaleOp(1f, brightness, null);
        rescaleOp.filter(image, image);
        return image;
    }

    private BufferedImage adjustContrast(BufferedImage image, int contrast) {
        float scaleFactor = 1 + (contrast / 100f);
        RescaleOp rescaleOp = new RescaleOp(scaleFactor, 0, null);
        rescaleOp.filter(image, image);
        return image;
    }

    private BufferedImage applyGrayscaleFilter(BufferedImage image) {
        BufferedImage grayscaleImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int rgb = image.getRGB(x, y);
                int alpha = (rgb >> 24) & 0xff;
                int red = (rgb >> 16) & 0xff;
                int green = (rgb >> 8) & 0xff;
                int blue = rgb & 0xff;
                int gray = (red + green + blue) / 3;
                int newRgb = (alpha << 24) | (gray << 16) | (gray << 8) | gray;
                grayscaleImage.setRGB(x, y, newRgb);
            }
        }
        return grayscaleImage;
    }
}