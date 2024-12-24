package backend.academy.render.renderer;

import backend.academy.transformation.LinearTransformation;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CachedThreadExecutorServiceFractalRenderer extends AbstractExecutorServiceFractalRenderer {

    public CachedThreadExecutorServiceFractalRenderer(
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
    protected ExecutorService getExecutor() {
        return Executors.newCachedThreadPool();
    }
}
