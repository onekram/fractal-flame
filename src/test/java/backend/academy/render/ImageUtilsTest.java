package backend.academy.render;

import java.util.Random;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ImageUtilsTest {

    @RepeatedTest(100)
    @DisplayName("Gamma correction correctness")
    void gammaCorrection() {
        Random random = new Random();
        int size = 10;
        FractalImage corrected = FractalImage.create(size, size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                corrected.pixel(i, j).red(random.nextInt(100, 255));
                corrected.pixel(i, j).green(random.nextInt(100, 255));
                corrected.pixel(i, j).blue(random.nextInt(100, 255));
                corrected.pixel(i, j).hitCount(random.nextInt(100, 1000));
            }
        }

        FractalImage origin = FractalImage.create(size, size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                origin.pixel(i, j).red(corrected.pixel(i, j).red());
                origin.pixel(i, j).blue(corrected.pixel(i, j).blue());
                origin.pixel(i, j).green(corrected.pixel(i, j).green());
                origin.pixel(i, j).hitCount(corrected.pixel(i, j).hitCount());
            }
        }
        ImageUtils.gammaCorrection(corrected, 0.5);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int x = i + 1; i < size; i++) {
                    for (int y = j + 1; j < size; j++) {
                        Pixel firstOrigin = origin.pixel(i, j);
                        Pixel secondOrigin = origin.pixel(x, y);
                        Pixel firstCorrected = corrected.pixel(i, j);
                        Pixel secondCorrected = corrected.pixel(x, y);
                        if (firstOrigin.hitCount() >= secondOrigin.hitCount()) {
                            assertThat(firstOrigin.red() / firstCorrected.red()).isLessThanOrEqualTo(
                                secondOrigin.red() / secondCorrected.red());
                            assertThat(firstOrigin.blue() / firstCorrected.blue()).isLessThanOrEqualTo(
                                secondOrigin.blue() / secondCorrected.blue());
                            assertThat(firstOrigin.green() / firstCorrected.green()).isLessThanOrEqualTo(
                                secondOrigin.green() / secondCorrected.green());
                        } else {
                            assertThat(firstOrigin.red() / firstCorrected.red()).isGreaterThanOrEqualTo(
                                secondOrigin.red() / secondCorrected.red());
                            assertThat(firstOrigin.blue() / firstCorrected.blue()).isGreaterThanOrEqualTo(
                                secondOrigin.blue() / secondCorrected.blue());
                            assertThat(firstOrigin.green() / firstCorrected.green()).isGreaterThanOrEqualTo(
                                secondOrigin.green() / secondCorrected.green());
                        }
                    }
                }
            }
        }

    }

    @Test
    @DisplayName("Correctness test for pixel averaging")
    void pixelAveraging() {
        FractalImage fractalImage = FractalImage.create(2, 2);
        fractalImage.pixel(0, 0).red(1);
        fractalImage.pixel(1, 0).red(8);
        fractalImage.pixel(0, 1).red(3);
        fractalImage.pixel(1, 1).red(4);

        FractalImage avg = ImageUtils.pixelAveraging(fractalImage, 2);
        assertEquals(1, avg.width());
        assertEquals(1, avg.height());
        assertEquals(4, avg.pixel(0, 0).red());

        fractalImage = FractalImage.create(3, 3);
        for (int i = 0; i < fractalImage.width(); i++) {
            for (int j = 0; j < fractalImage.height(); j++) {
                fractalImage.pixel(i, j).red(i);
            }
        }

        avg = ImageUtils.pixelAveraging(fractalImage, 2);
        assertEquals(1, avg.width());
        assertEquals(1, avg.height());
        assertEquals(0, avg.pixel(0, 0).red());

        fractalImage = FractalImage.create(6, 4);
        for (int i = 0; i < fractalImage.width(); i++) {
            for (int j = 0; j < fractalImage.height(); j++) {
                fractalImage.pixel(i, j).red(i);
            }
        }

        avg = ImageUtils.pixelAveraging(fractalImage, 2);
        assertEquals(3, avg.width());
        assertEquals(2, avg.height());

        assertEquals(0, avg.pixel(0, 0).red());
        assertEquals(2, avg.pixel(1, 0).red());
        assertEquals(4, avg.pixel(2, 0).red());

        assertEquals(0, avg.pixel(0, 1).red());
        assertEquals(2, avg.pixel(1, 1).red());
        assertEquals(4, avg.pixel(2, 1).red());
    }
}
