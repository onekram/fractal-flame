package backend.academy.render;

import backend.academy.render.shapes.Point;
import backend.academy.render.shapes.Rect;
import backend.academy.transformation.LinearTransformation;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;



class FractalRendererTest {

    @Test
    @DisplayName("Correctness render test")
    void render() {
        LinearTransformation linearTransformation = Mockito.mock(LinearTransformation.class);
        when(linearTransformation.apply(any())).thenReturn(new Point(0, 2));
        when(linearTransformation.red()).thenReturn(255);
        when(linearTransformation.blue()).thenReturn(255);
        when(linearTransformation.green()).thenReturn(255);

        FractalImage image = FractalImage.create(5, 5);
        image = FractalRenderer.render(image, Rect.getMirror(2, 2), List.of(linearTransformation), 1, 3, 4);

        assertNotNull(image);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (i == 0 && j == 2 || i == 2 && j == 0 || i == 4 && j == 2 || i == 2 && j == 4) {
                    assertThat(image.pixel(i, j).hitCount()).isEqualTo(3);
                    assertThat(image.pixel(i, j).r()).isEqualTo(255);
                    assertThat(image.pixel(i, j).g()).isEqualTo(255);
                    assertThat(image.pixel(i, j).b()).isEqualTo(255);
                } else {
                    assertThat(image.pixel(i, j).hitCount()).isEqualTo(0);
                    assertThat(image.pixel(i, j).r()).isEqualTo(0);
                    assertThat(image.pixel(i, j).g()).isEqualTo(0);
                    assertThat(image.pixel(i, j).b()).isEqualTo(0);
                }
            }
        }
    }
}
