package backend.academy.display;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ResourceBundle;
import javax.swing.JFrame;
import javax.swing.JPanel;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FractalImageDisplay {
    public static void display(BufferedImage image) {
        ResourceBundle bundle = ResourceBundle.getBundle("messages");
        String title = bundle.getString("image.display.title");

        JFrame frame = new JFrame(title);
        ImageDisplay panel = new ImageDisplay(image);
        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private static class ImageDisplay extends JPanel {
        private final BufferedImage image;

        ImageDisplay(BufferedImage image) {
            this.image = image;
            setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, null);
        }
    }
}
