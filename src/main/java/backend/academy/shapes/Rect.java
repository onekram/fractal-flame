package backend.academy.shapes;

import java.security.SecureRandom;
import java.util.Random;

public record Rect (double x, double y, double width, double height) {
    private static final Random RANDOM = new SecureRandom();
    public boolean contains(Point p) {
        return p.x() >= x && p.x() <= x + width && p.y() >= y && p.y() <= y + height;
    }

    public Point randomPoint() {
        return new Point(x + RANDOM.nextDouble(width), y + RANDOM.nextDouble(height));
    }
}
