package backend.academy.render.renderer;

import backend.academy.render.FractalImage;
import backend.academy.render.shapes.Point;
import backend.academy.render.shapes.Rect;
import backend.academy.transformation.LinearTransformation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public abstract class AbstractRendererTest {
    @Test
    @DisplayName("Correctness render test")
    void render() {
        LinearTransformation linearTransformation = Mockito.mock(LinearTransformation.class);
        when(linearTransformation.apply(any())).thenReturn(new Point(0, 2));
        when(linearTransformation.red()).thenReturn(255);
        when(linearTransformation.blue()).thenReturn(255);
        when(linearTransformation.green()).thenReturn(255);

        FractalImage image = FractalImage.create(5, 5);
        getRenderer().render(image, Rect.getMirror(2, 2), List.of(linearTransformation), 1, 3, 4, false, false);

        assertNotNull(image);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (i == 0 && j == 2 || i == 2 && j == 0 || i == 4 && j == 2 || i == 2 && j == 4) {
                    assertThat(image.pixel(i, j).hitCount()).isEqualTo(3);
                    assertThat(image.pixel(i, j).red()).isEqualTo(255);
                    assertThat(image.pixel(i, j).green()).isEqualTo(255);
                    assertThat(image.pixel(i, j).blue()).isEqualTo(255);
                } else {
                    assertThat(image.pixel(i, j).hitCount()).isEqualTo(0);
                    assertThat(image.pixel(i, j).red()).isEqualTo(0);
                    assertThat(image.pixel(i, j).green()).isEqualTo(0);
                    assertThat(image.pixel(i, j).blue()).isEqualTo(0);
                }
            }
        }
    }
    protected abstract Renderer getRenderer();
}
