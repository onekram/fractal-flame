package backend.academy.render.renderer;

import backend.academy.render.FractalImage;
import backend.academy.render.shapes.Rect;
import backend.academy.transformation.LinearTransformation;
import backend.academy.utils.RandomPicker;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import lombok.RequiredArgsConstructor;

public class ForkJoinPoolFractalRenderer extends AbstractFractalRenderer {
    public ForkJoinPoolFractalRenderer(
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
        try (ForkJoinPool pool = new ForkJoinPool()) {
            pool.invoke(
                new RenderTask(canvas, world, new RandomPicker<>(transformations), samples));
        }
    }

    @RequiredArgsConstructor
    private class RenderTask extends RecursiveAction {
        private final FractalImage canvas;
        private final Rect world;
        private final RandomPicker<LinearTransformation> picker;
        private final int curSamples;

        @Override
        protected void compute() {
            if (curSamples <= 1) {
                renderSample(canvas, world, picker);
            } else {
                int mid = curSamples / 2;
                invokeAll(
                    new RenderTask(canvas, world, picker, mid),
                    new RenderTask(canvas, world, picker, curSamples - mid));
            }
        }
    }
}
