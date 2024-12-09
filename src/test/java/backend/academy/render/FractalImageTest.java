package backend.academy.render;

import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FractalImageTest {

    @Test
    @DisplayName("Create correctness test")
    void create() {
        FractalImage image = FractalImage.create(10, 10);

        assertThat(image.width()).isEqualTo(10);
        assertThat(image.height()).isEqualTo(10);
        assertThat(image.data().length).isEqualTo(100);
        for (int i = 0; i < 100; i++) {
            assertThat(image.data()[i]).isEqualTo(new Pixel(0, 0, 0, 0));
        }
    }

    @Test
    @DisplayName("Get pixel test")
    void pixel() {
        Pixel[] pixels = new Pixel[100];
        Arrays.setAll(pixels, i -> new Pixel(i, i, i, i));
        FractalImage image = new FractalImage(pixels, 10, 10);

        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                assertEquals(image.pixel(i, j), new Pixel(i + 10 * j, i + 10 * j, i + 10 * j, i + 10 * j));
            }
        }
    }
}
