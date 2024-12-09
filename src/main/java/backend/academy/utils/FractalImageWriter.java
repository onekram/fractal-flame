package backend.academy.utils;

import backend.academy.argparser.ImageFormat;
import backend.academy.render.FractalImage;
import backend.academy.render.Pixel;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FractalImageWriter {
    public static void write(FractalImage fractal, ImageFormat format, Path outputPath) {
        BufferedImage image = new BufferedImage(fractal.width(), fractal.height(), BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < fractal.width(); x++) {
            for (int y = 0; y < fractal.height(); y++) {
                Pixel pixel = fractal.pixel(x, y);
                Color color = new Color(pixel.r(), pixel.g(), pixel.b());
                image.setRGB(x, y, color.getRGB());
            }
        }
        format.writeImage(image, outputPath);
    }
}
