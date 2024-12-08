package backend.academy.render;

import backend.academy.shapes.Point;
import backend.academy.shapes.Rect;
import java.util.List;
import java.util.Random;
import backend.academy.transformation.Transformation;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FractalRenderer {
    public static FractalImage render(
        FractalImage canvas,
        Rect world,
        List<Transformation> transformations,
        int samples,
        int iterPerSample,
        long seed
    ) {
        Random random = new Random(seed);
        int symmetry = 8;
        for (int i = 0; i < samples; i++) {
            Point p = world.randomPoint();

            for (int j = 0; j < iterPerSample; j++) {
                Transformation transformation = randomVariation(transformations, random);
                p = transformation.apply(p);
                double angle = 0;
                for (int s = 0; s < symmetry; angle += Math.PI * 2 / symmetry, ++s) {
                    Point pr = rotate(p, angle);
                    if (!world.contains(pr)) continue;

                    Pixel pixel = mapRange(world, pr, canvas);

                    int newR = Math.min(255, pixel.r() + 1);
                    int newG = Math.min(255, pixel.g() + 1);
                    int newB = Math.min(255, pixel.b() + 1);
                    int newHitCount = pixel.hitCount() + 1;

                    Pixel newPixel = new Pixel(newR, newG, newB, newHitCount);
                    canvas.data()[pixelIndex(canvas, pr)] = newPixel;
                }
            }
        }
        return canvas;
    }

    private static Transformation randomVariation(List<Transformation> variations, Random random) {
        return variations.get(random.nextInt(variations.size()));
    }

    private static Point rotate(Point p, double angle) {
        double cosTheta = Math.cos(angle);
        double sinTheta = Math.sin(angle);
        double newX = p.x() * cosTheta - p.y() * sinTheta;
        double newY = p.x() * sinTheta + p.y() * cosTheta;
        return new Point(newX, newY);
    }

    private static Pixel mapRange(Rect world, Point p, FractalImage canvas) {
        int x = (int) ((p.x() - world.x()) / world.width() * canvas.width());
        int y = (int) ((p.y() - world.y()) / world.height() * canvas.height());

        return canvas.data()[y * canvas.width() + x];
    }

    private int pixelIndex(FractalImage canvas, Point p) {
        int x = (int) p.x();
        int y = (int) p.y();
        return y * canvas.width() + x;
    }
}
