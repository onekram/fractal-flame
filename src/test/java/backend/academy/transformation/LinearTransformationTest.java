package backend.academy.transformation;

import backend.academy.render.shapes.Point;
import java.lang.reflect.Field;
import java.util.List;
import org.assertj.core.data.Offset;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class LinearTransformationTest {

    @Test
    @DisplayName("Correctness test for apply")
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

    @Test
    @DisplayName("Correctness test generating from nonlinear")
    void fromNonlinearTransformations() {
        List<NonlinearTransformation> transformations =
            List.of(NonlinearTransformation.SINUSOIDAL, NonlinearTransformation.FISHEYE,
                NonlinearTransformation.BUBBLE);
        LinearTransformation res = LinearTransformation.fromNonlinearTransformations(transformations);

        assertThat(res).isNotNull();
        assertThat(res.red()).isBetween(0, 255);
        assertThat(res.blue()).isBetween(0, 255);
        assertThat(res.green()).isBetween(0, 255);
        assertThat(res.coefficients().size()).isEqualTo(6);
        for (int i = 0; i < 6; i++) {
            assertThat(res.coefficients().get(i)).isBetween(-1.5, 1.5);
        }
        assertThat(res.variations().size()).isEqualTo(transformations.size());
        assertThat(res.variations().stream().map(Variation::weight).mapToDouble(i -> i).sum())
            .isCloseTo(1.0, Offset.offset(0.001));
        for (int i = 0; i < res.variations().size(); i++) {
            assertThat(res.variations().get(i).func()).isEqualTo(transformations.get(i));
        }
    }
}
