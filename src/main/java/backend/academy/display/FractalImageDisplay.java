package backend.academy.display;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FractalImageDisplay {
    public static void display(BufferedImage image) {
        JFrame frame = new JFrame("Image Display");
        ImageDisplay panel = new ImageDisplay(image);
        frame.add(panel);
        frame.setSize(image.getWidth(), image.getHeight());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @RequiredArgsConstructor
    private static class ImageDisplay extends JPanel {
        private final BufferedImage image;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, null);
        }
    }
}
