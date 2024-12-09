package backend.academy.transformation;

import java.lang.reflect.Field;
import java.util.List;
import backend.academy.render.shapes.Point;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class LinearTransformationTest {

    @Test
    @DisplayName("Correctness test")
    void apply() throws NoSuchFieldException, IllegalAccessException {
        LinearTransformation linearTransformation = new LinearTransformation();
        Field variationsField = LinearTransformation.class.getDeclaredField("variations");
        variationsField.setAccessible(true);
        variationsField.set(linearTransformation, List.of(new Variation(0.3, NonlinearTransformation.SINUSOIDAL),
            new Variation(0.7, NonlinearTransformation.SPHERICAL)));

        Field coefficientsField = LinearTransformation.class.getDeclaredField("coefficients");
        coefficientsField.setAccessible(true);
        coefficientsField.set(linearTransformation, List.of(1.0, 2.0, 3.0, 1.0, 2.0, 3.0));
        Point res = linearTransformation.apply(new Point(1, 1));
        assertThat(res.x()).isCloseTo(-0.025, Percentage.withPercentage(3));
        assertThat(res.y()).isCloseTo(-0.025, Percentage.withPercentage(3));
    }
}
