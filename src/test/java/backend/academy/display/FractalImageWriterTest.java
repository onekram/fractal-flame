package backend.academy.display;

import backend.academy.render.FractalImage;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.Random;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FractalImageWriterTest {
    @TempDir
    Path tempDir;

    @Test
    @DisplayName("Correctness test for write")
    void write() {
        Random random = new Random();
        FractalImage fractal = FractalImage.create(100, 100);
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                fractal.pixel(i, j).red(random.nextInt(255));
                fractal.pixel(i, j).green(random.nextInt(255));
                fractal.pixel(i, j).blue(random.nextInt(255));
            }
        }
        Path file = tempDir.resolve("fractal.png");
        BufferedImage res = FractalImageWriter.write(fractal, ImageFormat.PNG, file);

        assertNotNull(res);
        assertEquals(fractal.width(), res.getWidth());
        assertEquals(fractal.height(), res.getHeight());

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                assertEquals(fractal.pixel(i, j).red(), (res.getRGB(i, j) >> 16) & 0xff);
                assertEquals(fractal.pixel(i, j).green(), (res.getRGB(i, j) >> 8) & 0xff);
                assertEquals(fractal.pixel(i, j).blue(), res.getRGB(i, j) & 0xff);
            }
        }
    }
}
