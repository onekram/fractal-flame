package backend.academy.render.shapes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class RectTest {

    @Test
    @DisplayName("Contains test")
    void contains() {
        Rect rect = new Rect(-5, 5, -100, 100);

        assertTrue(rect.contains(new Point(5, 5)));
        assertTrue(rect.contains(new Point(-5, 5)));
        assertTrue(rect.contains(new Point(3, 100)));
        assertTrue(rect.contains(new Point(5, 100)));
        assertTrue(rect.contains(new Point(-5, 100)));
        assertTrue(rect.contains(new Point(-5, 50)));
        assertTrue(rect.contains(new Point(-5, -100)));
        assertTrue(rect.contains(new Point(0, 0)));

        assertFalse(rect.contains(new Point(-5.000000001, 0)));
        assertFalse(rect.contains(new Point(5.000000001, 0)));
        assertFalse(rect.contains(new Point(0, 100.000000000001)));
        assertFalse(rect.contains(new Point(0, -100.000000000001)));
        assertFalse(rect.contains(new Point(10000, 10000)));
    }

    @Test
    @DisplayName("Get mirror rect test")
    void getMirror() {
        Random random = new Random();
        int x = random.nextInt(100);
        int y = random.nextInt(100);
        assertThat(Rect.getMirror(x, y)).isEqualTo(new Rect(-x, x, -y, y));
    }

    @RepeatedTest(5)
    @DisplayName("Random point test")
    void randomPoint() {
        Random random = new Random();
        int x = random.nextInt(100);
        int y = random.nextInt(100);
        Rect rect = Rect.getMirror(x, y);

        assertTrue(rect.contains(rect.randomPoint()));
    }
}
