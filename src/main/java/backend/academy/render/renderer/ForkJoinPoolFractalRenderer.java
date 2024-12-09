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
    @Override
    public void render(
        FractalImage canvas,
        Rect world,
        List<LinearTransformation> transformations,
        int samples,
        int iterPerSample,
        int rotations
    ) {
        try (ForkJoinPool pool = new ForkJoinPool()) {
            pool.invoke(
                new RenderTask(canvas, world, new RandomPicker<>(transformations), samples, iterPerSample, rotations));
        }
    }

    @RequiredArgsConstructor
    private class RenderTask extends RecursiveAction {
        private final FractalImage canvas;
        private final Rect world;
        private final RandomPicker<LinearTransformation> picker;
        private final int samples;
        private final int iterPerSample;
        private final int rotations;

        @Override
        protected void compute() {
            if (samples <= 1) {
                renderSample(canvas, world, picker, iterPerSample, rotations);
            } else {
                int mid = samples / 2;
                invokeAll(
                    new RenderTask(canvas, world, picker, mid, iterPerSample, rotations),
                    new RenderTask(canvas, world, picker, samples - mid, iterPerSample, rotations));
            }
        }
    }
}
