package backend.academy.gui;

import backend.academy.configparser.ConfigParser;
import backend.academy.render.FractalImage;
import backend.academy.render.FractalRenderer;
import backend.academy.render.Pixel;
import backend.academy.shapes.Rect;
import backend.academy.transformation.LinearTransformation;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.util.List;

public class FractalImageRender {
    public static void main(String[] args) throws IOException {
        int width = 500;
        int height = 500;
        Pixel[] pixels = new Pixel[width * height];
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = new Pixel(0, 0, 0, 0);
        }
        FractalImage fractalImage = new FractalImage(pixels, width, height);
        List<LinearTransformation> transformations = ConfigParser.parse("file.json");
        fractalImage =
            FractalRenderer.render(fractalImage, new Rect(-2, 2, -2, 2), transformations, 100, 1000, 12);

        JFrame frame = new JFrame("Fractal Image Viewer");
        FractalPanel panel = new FractalPanel(fractalImage);
        frame.add(panel);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static class FractalPanel extends JPanel {
        private FractalImage fractalImage;

        public FractalPanel(FractalImage fractalImage) {
            this.fractalImage = fractalImage;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (fractalImage != null) {
                Pixel[] data = fractalImage.data();
                int width = fractalImage.width();
                int height = fractalImage.height();

                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        Pixel pixel = data[y * width + x];
                        g.setColor(new Color(pixel.r(), pixel.g(), pixel.b()));
                        g.drawLine(x, y, x, y); // Draw a single pixel
                    }
                }
            }
        }
    }
}
