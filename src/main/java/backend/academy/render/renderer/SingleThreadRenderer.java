package backend.academy.render.renderer;

import backend.academy.render.FractalImage;
import backend.academy.render.shapes.Rect;
import backend.academy.transformation.LinearTransformation;
import backend.academy.utils.RandomPicker;
import java.util.List;

public class SingleThreadRenderer extends AbstractFractalRenderer {
    public SingleThreadRenderer(
        List<LinearTransformation> transformations,
        int samples,
        int iterPerSample,
        int rotations,
        boolean symmetricX,
        boolean symmetricY
    ) {
        super(transformations, samples, iterPerSample, rotations, symmetricX, symmetricY);
    }

    @Override
    public void render(
        FractalImage canvas,
        Rect world
    ) {
        RandomPicker<LinearTransformation> picker = new RandomPicker<>(transformations);
        for (int i = 0; i < samples; i++) {
            renderSample(canvas, world, picker);
        }
    }
}
