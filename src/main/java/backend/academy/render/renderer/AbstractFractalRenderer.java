package backend.academy.render.renderer;

import backend.academy.render.FractalImage;
import backend.academy.render.Pixel;
import backend.academy.render.shapes.Point;
import backend.academy.render.shapes.Rect;
import backend.academy.transformation.LinearTransformation;
import backend.academy.utils.RandomPicker;
import java.util.List;

public abstract class AbstractFractalRenderer implements Renderer {
    private static final int SKIP_STEPS = 20;

    @Override
    public abstract void render(
        FractalImage canvas,
        Rect world,
        List<LinearTransformation> transformations,
        int samples,
        int iterPerSample,
        int rotations
    );

    protected void renderSample(
        FractalImage canvas,
        Rect world,
        RandomPicker<LinearTransformation> picker,
        int iterPerSample,
        int rotations
    ) {
        Point p = world.randomPoint();

        for (int j = -SKIP_STEPS; j < iterPerSample; j++) {
            LinearTransformation transformation = picker.pick();
            p = transformation.apply(p);
            if (j >= 0) {
                double angle = 0;
                for (int s = 0; s < rotations; angle += Math.PI * 2 / rotations, ++s) {
                    Point pr = rotate(p, angle);
                    if (world.contains(pr)) {
                        Pixel pixel = mapRange(pr, world, canvas);
                        pixel.paint(transformation);
                    }
                }
            }
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
        double normalizedX = (p.x() - world.xMin()) / (world.xMax() - world.xMin());
        double normalizedY = (p.y() - world.yMin()) / (world.yMax() - world.yMin());

        int x = (int) Math.round(normalizedX * (canvas.width() - 1));
        int y = (int) Math.round(normalizedY * (canvas.height() - 1));

        return canvas.pixel(x, y);
    }
}
