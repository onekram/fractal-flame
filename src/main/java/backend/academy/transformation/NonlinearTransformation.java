package backend.academy.transformation;

import backend.academy.render.shapes.Point;
import backend.academy.utils.RandomUtils;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum NonlinearTransformation implements Transformation {
    LINEAR(point -> new Point(point.x(), point.y())),
    SINUSOIDAL(point -> new Point(Math.sin(point.x()), Math.sin(point.y()))),
    SPHERICAL(point -> {
        double r = point.r();
        return new Point(point.x() / (r * r), point.y() / (r * r));
    }),
    SWIRL(point -> {
        double r = point.r();
        return new Point(
            point.x() * Math.sin(r * r) - point.y() * Math.cos(r * r),
            point.x() * Math.cos(r * r) + point.y() * Math.sin(r * r)
        );
    }),
    HORSESHOE(point -> {
        double r = point.r();
        return new Point(
            (1 / r) * (point.x() - point.y()) * (point.x() + point.y()),
            (1 / r) * 2 * point.x() * point.y()
        );
    }),
    POLAR(point -> {
        double r = point.r();
        double th = Math.atan2(point.y(), point.x());
        return new Point(th / Math.PI, r - 1);
    }),
    HANKERCHIEF(point -> {
        double r = point.r();
        double th = Math.atan2(point.y(), point.x());
        return new Point(r * Math.sin(th + r), r * Math.cos(th - r));
    }),
    HEART(point -> {
        double r = point.r();
        double th = Math.atan2(point.y(), point.x());
        return new Point(r * Math.sin(th * r), r * -Math.cos(th * r));
    }),
    DISC(point -> {
        double r = point.r();
        double th = Math.atan2(point.y(), point.x());
        return new Point(
            (th / Math.PI) * Math.sin(Math.PI * r),
            (th / Math.PI) * Math.cos(Math.PI * r)
        );
    }),
    SPIRAL(point -> {
        double r = point.r();
        double th = Math.atan2(point.y(), point.x());
        return new Point(
            (1 / r) * (Math.cos(th) + Math.sin(r)),
            (1 / r) * (Math.sin(th) - Math.cos(r))
        );
    }),
    HYPERBOLIC(point -> {
        double r = point.r();
        double th = Math.atan2(point.y(), point.x());
        return new Point(Math.sin(th) / r, r * Math.cos(th));
    }),
    DIAMOND(point -> {
        double r = point.r();
        double th = Math.atan2(point.y(), point.x());
        return new Point(Math.sin(th) * Math.cos(r), Math.cos(th) * Math.sin(r));
    }),
    @SuppressWarnings("MagicNumber")
    EX(point -> {
        double r = point.r();
        double th = Math.atan2(point.y(), point.x());
        double p0 = Math.sin(th + r);
        double p1 = Math.cos(th - r);
        return new Point(r * (Math.pow(p0, 3) + Math.pow(p1, 3)), r * (Math.pow(p0, 3) - Math.pow(p1, 3)));
    }),
    BENT(point -> {
        if (point.x() >= 0 && point.y() >= 0) {
            return new Point(point.x(), point.y());
        } else if (point.x() < 0 && point.y() >= 0) {
            return new Point(2 * point.x(), point.y());
        } else if (point.x() >= 0 && point.y() < 0) {
            return new Point(point.x(), point.y() / 2);
        } else {
            return new Point(2 * point.x(), point.y() / 2);
        }
    }),
    WAVES(point -> {
        double c1 = 1;
        double c2 = 1;
        return new Point(
            point.x() + (c1 * Math.sin(point.y() / Math.pow(c2, 2))),
            point.y() + (c2 * Math.sin(point.x() / Math.pow(c2, 2)))
        );
    }),
    FISHEYE(point -> {
        double re = 2 / (Math.sqrt(Math.pow(point.x(), 2) + Math.pow(point.y(), 2)) + 1);
        return new Point(re * point.y(), re * point.x());
    }),
    @SuppressWarnings("MagicNumber")
    POPCORN(point -> {
        double c2 = 1;
        double c5 = 1;
        return new Point(
            point.x() + (c2 * Math.sin(Math.tan(3 * point.y()))),
            point.y() + (c5 * Math.sin(Math.tan(3 * point.x())))
        );
    }),
    POWER(point -> {
        double th = Math.atan2(point.y(), point.x());
        double rsth = Math.pow(Math.sqrt(Math.pow(point.x(), 2) + Math.pow(point.y(), 2)), Math.sin(th));
        return new Point(rsth * Math.cos(th), rsth * Math.sin(th));
    }),
    EYEFISH(point -> {
        double re = 2 / (Math.sqrt(Math.pow(point.x(), 2) + Math.pow(point.y(), 2)) + 1);
        return new Point(re * point.x(), re * point.y());
    }),
    @SuppressWarnings("MagicNumber")
    BUBBLE(point -> {
        double re = 4 / (Math.pow(Math.sqrt(Math.pow(point.x(), 2) + Math.pow(point.y(), 2)), 2) + 4);
        return new Point(re * point.x(), re * point.y());
    }),
    CYLINDER(point -> new Point(Math.sin(point.x()), point.y())),
    TANGENT(point -> new Point(Math.sin(point.x()) / Math.cos(point.y()), Math.tan(point.y()))),
    NOISE(point -> {
        double p1 = RandomUtils.nextDouble();
        double p2 = RandomUtils.nextDouble();
        return new Point(p1 * point.x() * Math.cos(2 * Math.PI * p2), p1 * point.y() * Math.sin(2 * Math.PI * p2));
    }),
    BLUR(point -> {
        double p1 = RandomUtils.nextDouble();
        double p2 = RandomUtils.nextDouble();
        return new Point(p1 * Math.cos(2 * Math.PI * p2), p1 * Math.sin(2 * Math.PI * p2));
    }),
    CROSS(point -> {
        double s = Math.sqrt(1 / Math.pow(Math.pow(point.x(), 2) - Math.pow(point.y(), 2), 2));
        return new Point(s * point.x(), s * point.y());
    }),
    @SuppressWarnings("MagicNumber")
    SQUARE(point -> {
        double p1 = RandomUtils.nextDouble();
        double p2 = RandomUtils.nextDouble();
        return new Point(p1 - 0.5, p2 - 0.5);
    });
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
