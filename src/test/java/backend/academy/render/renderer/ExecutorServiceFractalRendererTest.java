package backend.academy.render.renderer;

import backend.academy.transformation.LinearTransformation;
import java.util.List;

public class ExecutorServiceFractalRendererTest extends AbstractRendererTest {
    @Override
    protected Renderer getRenderer(
        List<LinearTransformation> transformations,
        int samples,
        int iterPerSample,
        int rotations,
        boolean symmetricX,
        boolean symmetricY
    ) {
        return new ExecutorServiceFractalRenderer(transformations, samples, iterPerSample, rotations, symmetricX, symmetricY);
    }
}
