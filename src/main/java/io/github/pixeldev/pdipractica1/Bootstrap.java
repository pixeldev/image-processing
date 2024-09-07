package io.github.pixeldev.pdipractica1;

import javax.swing.*;

public final class Bootstrap {
    private Bootstrap() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ImageViewer().setVisible(true));
    }
}
