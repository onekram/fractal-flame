package backend.academy.transformation;

import backend.academy.render.shapes.Point;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TransformationTest {

    @Test
    @DisplayName("Correct construct from coeffs")
    void fromCoefficients() {
        Transformation transformation = Transformation.fromCoefficients(List.of(1.0, 2.0, 3.0, 1.0, 2.0, 3.0));
        assertNotNull(transformation);

        Point point = new Point(1.0, 2.0);
        assertThat(transformation.apply(point)).isEqualTo(new Point(8, 8));
    }
}
