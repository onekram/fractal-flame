package backend.academy.render.shapes;

import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PointTest {

    @Test
    @DisplayName("Distance to 0 0")
    void r() {
        assertThat(new Point(0, 0).r()).isEqualTo(0);

        assertThat(new Point(3, 4).r()).isEqualTo(5);

        assertThat(new Point(-3, -4).r()).isEqualTo(5);

        assertThat(new Point(3, 5).r()).isCloseTo(5.8, Percentage.withPercentage(2));
    }

    @ParameterizedTest(name = "{index} test for points ({0}, {1}) and ({2}, {3})")
    @CsvSource({
        "0, 0, 0, 0, 0, 0",
        "0, 1, 0, 1, 0, 2",
        "10, 2, 3, 4, 13, 6",
        "-100, 100, 100, -100, 0, 0",
        "1, 2, 3, 4, 4, 6",
    })
    @DisplayName("Test for add correctness")
    void add(double x1, double y1, double x2, double y2, double x3, double y3) {
        Point point1 = new Point(x1, y1);
        Point point2 = new Point(x2, y2);
        Point point3 = new Point(x3, y3);
        assertThat(point1.add(point2)).isEqualTo(point3);
    }
}
