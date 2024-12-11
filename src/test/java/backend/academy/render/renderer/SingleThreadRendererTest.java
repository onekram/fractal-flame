package backend.academy.render.renderer;

import backend.academy.transformation.LinearTransformation;

import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;

class SingleThreadRendererTest extends AbstractRendererTest{
    @Override
    protected Renderer getRenderer(
        List<LinearTransformation> transformations,
        int samples,
        int iterPerSample,
        int rotations,
        boolean symmetricX,
        boolean symmetricY
    ) {
        return new SingleThreadRenderer(transformations, samples, iterPerSample, rotations, symmetricX, symmetricY);
    }
}
