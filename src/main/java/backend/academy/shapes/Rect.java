package backend.academy.shapes;

import backend.academy.utils.RandomUtils;

public record Rect(double xMin, double xMax, double yMin, double yMax) {
    public boolean contains(Point p) {
        return p.x() >= xMin && p.x() <= xMax && p.y() >= yMin && p.y() <= yMax;
    }

    public Point randomPoint() {
        return new Point(RandomUtils.nextDoubleBetween(xMin, xMax), RandomUtils.nextDoubleBetween(yMin, yMax));
    }
}
