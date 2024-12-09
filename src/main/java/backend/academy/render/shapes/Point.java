package backend.academy.render.shapes;

public record Point(double x, double y) {
    public double r() {
        return Math.sqrt(x * x + y * y);
    }

    public Point add(Point other) {
        return new Point(x + other.x, y + other.y);
    }
}
