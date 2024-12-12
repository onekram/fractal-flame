package backend.academy.transformation;

import backend.academy.render.shapes.Point;
import java.util.stream.Stream;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class NonlinearTransformationTest {
    private static Stream<Arguments> provide() {
        return Stream.of(
            Arguments.of(NonlinearTransformation.LINEAR, new Point(10, 10), new Point(10, 10)),
            Arguments.of(NonlinearTransformation.SINUSOIDAL, new Point(Math.PI / 2, Math.PI / 2), new Point(1, 1)),
            Arguments.of(NonlinearTransformation.SPHERICAL, new Point(1, 1), new Point(0.5, 0.5)),
            Arguments.of(NonlinearTransformation.DIAMOND, new Point(1, Math.sqrt(3)),
                new Point(Math.sqrt(3) * 0.5 * Math.cos(2), 0.5 * Math.sin(2))),
            Arguments.of(NonlinearTransformation.HYPERBOLIC, new Point(1, Math.sqrt(3)),
                new Point(Math.sqrt(3) * 0.25, 1)),
            Arguments.of(NonlinearTransformation.DISC, new Point(1, Math.sqrt(3)), new Point(0, 0.333)),
            Arguments.of(NonlinearTransformation.POLAR, new Point(1, Math.sqrt(3)), new Point(0.33333, 1)),
            Arguments.of(NonlinearTransformation.TANGENT, new Point(0, 0), new Point(0, 0)),
            Arguments.of(NonlinearTransformation.HORSESHOE, new Point(1, 1), new Point(0, Math.sqrt(2))),
            Arguments.of(NonlinearTransformation.HANKERCHIEF, new Point(1, Math.sqrt(3)),
                new Point(2 * Math.sin(Math.PI / 3 + 2), 2 * Math.cos(Math.PI / 3 - 2))),
            Arguments.of(NonlinearTransformation.HEART, new Point(1, Math.sqrt(3)), new Point(Math.sqrt(3), 1)),
            Arguments.of(NonlinearTransformation.HEART, new Point(1, Math.sqrt(3)), new Point(Math.sqrt(3), 1)),
            Arguments.of(NonlinearTransformation.SWIRL, new Point(1, 0), new Point(Math.sin(1), Math.cos(1)))
        );
    }

    @ParameterizedTest(name = "{index} test for function {0}")
    @MethodSource("provide")
    @DisplayName("Correctness test")
    void correctnessTest(NonlinearTransformation func, Point point, Point expected) {
        Point res = func.apply(point);
        assertThat(res.x()).isCloseTo(expected.x(), Offset.offset(0.01));
        assertThat(res.y()).isCloseTo(expected.y(), Offset.offset(0.01));
    }
}
