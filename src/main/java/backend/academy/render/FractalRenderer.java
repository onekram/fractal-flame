package backend.academy.render;

import backend.academy.render.shapes.Point;
import backend.academy.render.shapes.Rect;
import backend.academy.transformation.LinearTransformation;
import backend.academy.utils.RandomPicker;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FractalRenderer {
    private static final int SKIP_STEPS = 20;

    public static FractalImage render(
        FractalImage canvas,
        Rect world,
        List<LinearTransformation> transformations,
        int samples,
        int iterPerSample,
        int rotations
    ) {
        RandomPicker<LinearTransformation> picker = new RandomPicker<>(transformations);
        for (int i = 0; i < samples; i++) {

            Point p = world.randomPoint();

            for (int j = -SKIP_STEPS; j < iterPerSample; j++) {
                LinearTransformation transformation = picker.pick();
                p = transformation.apply(p);
                if (j > 0) {
                    double angle = 0;
                    for (int s = 0; s < rotations; angle += Math.PI * 2 / rotations, ++s) {
                        Point pr = rotate(p, angle);
                        if (world.contains(pr)) {
                            Pixel pixel = mapRange(pr, world, canvas);
                            paintPixel(pixel, transformation);
                        }
                    }
                }
            }
        }
        return canvas;
    }

    private static void paintPixel(Pixel pixel, LinearTransformation transformation) {
        if (pixel.hitCount() == 0) {
            pixel.r(transformation.red());
            pixel.g(transformation.green());
            pixel.b(transformation.blue());
            pixel.hitCount(1);
        } else {
            pixel.r((pixel.r() + transformation.red()) / 2);
            pixel.g((pixel.g() + transformation.green()) / 2);
            pixel.b((pixel.b() + transformation.blue()) / 2);
            pixel.hitCount(pixel.hitCount() + 1);
        }
    }

    private static Point rotate(Point p, double angle) {
        double cosTheta = Math.cos(angle);
        double sinTheta = Math.sin(angle);
        double newX = p.x() * cosTheta - p.y() * sinTheta;
        double newY = p.x() * sinTheta + p.y() * cosTheta;
        return new Point(newX, newY);
    }

    private static Pixel mapRange(Point p, Rect world, FractalImage canvas) {
        int x = (int) ((p.x() - world.xMin()) / (world.xMax() - world.xMin()) * canvas.width());
        int y = (int) ((p.y() - world.yMin()) / (world.yMax() - world.yMin()) * canvas.height());

        return canvas.data()[y * canvas.width() + x];
    }
}
