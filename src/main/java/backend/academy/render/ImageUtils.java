package backend.academy.render;

import java.util.Arrays;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ImageUtils {
    public static void gammaCorrection(FractalImage fractal, double gamma) {
        double[] logFreq = new double[fractal.width() * fractal.height()];
        double maxLogFreq = 0;
        for (int x = 0; x < fractal.width(); x++) {
            for (int y = 0; y < fractal.height(); y++) {
                Pixel pixel = fractal.pixel(x, y);
                if (pixel.hitCount() > 0) {
                    double freq = Math.log10(pixel.hitCount());
                    logFreq[x * fractal.height() + y] = freq;
                    maxLogFreq = Math.max(maxLogFreq, freq);
                }
            }
        }
        for (int x = 0; x < fractal.width(); x++) {
            for (int y = 0; y < fractal.height(); y++) {
                Pixel pixel = fractal.pixel(x, y);
                double alpha = logFreq[x * fractal.height() + y] / maxLogFreq;
                pixel.red(prepareColor(pixel.red(), alpha, gamma));
                pixel.green(prepareColor(pixel.green(), alpha, gamma));
                pixel.blue(prepareColor(pixel.blue(), alpha, gamma));
            }
        }
    }

    public static FractalImage pixelAveraging(FractalImage fractal, int boxSize) {
        int newWidth = fractal.width() / boxSize;
        int newHeight = fractal.height() / boxSize;
        Pixel[] newPixels = new Pixel[newWidth * newHeight];
        Arrays.setAll(newPixels, i -> new Pixel(0, 0, 0, 0));

        for (int x = 0; x < newWidth; x++) {
            for (int y = 0; y < newHeight; y++) {
                newPixels[x + newWidth * y] = getAveragePixel(fractal, boxSize, x, y);
            }
        }
        return new FractalImage(newPixels, newWidth, newHeight);
    }

    private static Pixel getAveragePixel(FractalImage fractal, int boxSize, int x, int y) {
        int r = 0;
        int g = 0;
        int b = 0;
        int h = 0;
        for (int i = 0; i < boxSize; i++) {
            for (int j = 0; j < boxSize; j++) {
                Pixel pixel = fractal.pixel(x * boxSize + i, y * boxSize + j);
                r += pixel.red();
                g += pixel.green();
                b += pixel.blue();
                h += pixel.hitCount();
            }
        }
        r /= boxSize * boxSize;
        g /= boxSize * boxSize;
        b /= boxSize * boxSize;
        h /= boxSize * boxSize;
        return new Pixel(r, g, b, h);
    }

    private static int prepareColor(int color, double alpha, double gamma) {
        return (int) Math.round(color * Math.pow(alpha, gamma));
    }
}
