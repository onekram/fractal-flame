package backend.academy.argparser;

import backend.academy.render.renderer.CachedThreadExecutorServiceFractalRenderer;
import backend.academy.render.renderer.Renderer;
import backend.academy.render.renderer.SingleThreadRenderer;
import backend.academy.transformation.LinearTransformation;
import java.util.List;

public enum ThreadRendererType {
    SINGLE {
        @Override
        public Renderer getRenderer(
            List<LinearTransformation> transformations,
            int samples,
            int iterPerSample,
            int rotations,
            boolean symmetricX,
            boolean symmetricY
        ) {
            return new SingleThreadRenderer(transformations, samples, iterPerSample, rotations, symmetricX, symmetricY);
        }
    },
    PARALLEL {
        @Override
        public Renderer getRenderer(
            List<LinearTransformation> transformations,
            int samples,
            int iterPerSample,
            int rotations,
            boolean symmetricX,
            boolean symmetricY
        ) {
            return new CachedThreadExecutorServiceFractalRenderer(transformations, samples, iterPerSample, rotations,
                symmetricX,
                symmetricY);
        }
    };

    public abstract Renderer getRenderer(
        List<LinearTransformation> transformations,
        int samples,
        int iterPerSample,
        int rotations,
        boolean symmetricX,
        boolean symmetricY
    );
}
