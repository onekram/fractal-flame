package backend.academy.render.renderer;

import backend.academy.render.FractalImage;
import backend.academy.render.shapes.Rect;
import backend.academy.transformation.LinearTransformation;
import backend.academy.utils.RandomPicker;
import java.util.List;

public class SingleThreadRenderer extends AbstractFractalRenderer {
    @Override
    public void render(
        FractalImage canvas,
        Rect world,
        List<LinearTransformation> transformations,
        int samples,
        int iterPerSample,
        int rotations,
        boolean symmetricX,
        boolean symmetricY
    ) {
        RandomPicker<LinearTransformation> picker = new RandomPicker<>(transformations);
        for (int i = 0; i < samples; i++) {
            renderSample(canvas, world, picker, iterPerSample, rotations, symmetricX, symmetricY);
        }
    }
}
