package backend.academy.render;

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

    private static int prepareColor(int color, double alpha, double gamma) {
        return (int) Math.round(color * Math.pow(alpha, gamma));
    }
}
