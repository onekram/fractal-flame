package backend.academy.transformation;

import backend.academy.shapes.Point;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum NonlinearTransformation implements Transformation {
    SINUSOIDAL(point -> new Point(Math.sin(point.x()), Math.sin(point.y()))),
    SPHERICAL(point -> new Point(point.x() / Math.pow(point.r(), 2), point.x() / Math.pow(point.r(), 2))),
    POLAR(point -> new Point(Math.atan(point.y() / point.x()) / Math.PI, point.r() - 1));

    private final Transformation transformation;

    @Override
    public Point apply(Point point) {
        return transformation.apply(point);
    }

    public static NonlinearTransformation fromString(String s) {
        for (NonlinearTransformation format : NonlinearTransformation.values()) {
            if (format.name().equalsIgnoreCase(s)) {
                return format;
            }
        }
        throw new IllegalArgumentException("Unknown transformation: " + s);
    }
}
