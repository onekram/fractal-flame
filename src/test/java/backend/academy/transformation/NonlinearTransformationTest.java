package backend.academy.transformation;

import backend.academy.render.shapes.Point;
import java.util.stream.Stream;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class NonlinearTransformationTest {
    private static Stream<Arguments> provide() {
        return Stream.of(
            Arguments.of(NonlinearTransformation.LINEAR, new Point(10, 10), new Point(10, 10)),
            Arguments.of(NonlinearTransformation.SINUSOIDAL, new Point(Math.PI / 2, Math.PI / 2), new Point(1, 1)),
            Arguments.of(NonlinearTransformation.SPHERICAL, new Point(1, 1), new Point(0.5, 0.5))
        );
    }

    @ParameterizedTest(name = "{index} test for function {0}")
    @MethodSource("provide")
    @DisplayName("Correctness test")
    void correctnessTest(NonlinearTransformation func, Point point, Point expected) {
        Point res = func.apply(point);
        assertThat(res.x()).isCloseTo(expected.x(), Percentage.withPercentage(1));
        assertThat(res.y()).isCloseTo(expected.y(), Percentage.withPercentage(1));
    }

    @Test
    @DisplayName("Get from string")
    void fromStringTest() {
        String s = "sPherIcAl";
        assertThat(NonlinearTransformation.fromString(s)).isEqualTo(NonlinearTransformation.SPHERICAL);
    }
}
