package backend.academy.shapes;

public record Point(double x, double y) {
    public double r() {
        return Math.sqrt(x * x + y * y);
    }
}
