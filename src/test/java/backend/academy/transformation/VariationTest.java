package backend.academy.transformation;

import backend.academy.render.shapes.Point;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class VariationTest {

    @Test
    @DisplayName("Correctness test")
    void apply() {
        Variation var = new Variation(10, NonlinearTransformation.SINUSOIDAL);
        assertThat(var.apply(new Point(Math.PI / 2, Math.PI / 2))).isEqualTo(new Point(10, 10));
    }
}
