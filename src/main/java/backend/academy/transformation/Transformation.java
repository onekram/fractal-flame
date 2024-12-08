package backend.academy.transformation;

import backend.academy.shapes.Point;
import java.util.List;
import java.util.function.Function;

public interface Transformation extends Function<Point, Point> {
    static Transformation fromCoefficients(List<Double> coefficients) {
        return point -> new Point(
            coefficients.get(0) * point.x() +
                coefficients.get(1) * point.y() +
                coefficients.get(2),
            coefficients.get(3) * point.x() +
                coefficients.get(4) * point.y() +
                coefficients.get(5));
    }
}
