package backend.academy.render.renderer;

import backend.academy.render.FractalImage;
import backend.academy.render.shapes.Rect;
import backend.academy.transformation.LinearTransformation;
import backend.academy.utils.RandomPicker;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorServiceFractalRenderer extends AbstractFractalRenderer {
    public ExecutorServiceFractalRenderer(
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
        try (ExecutorService executor = Executors.newCachedThreadPool()) {
            RandomPicker<LinearTransformation> picker = new RandomPicker<>(transformations);
            List<Future<?>> futures = new ArrayList<>(samples);
            for (int i = 0; i < samples; i++) {
                futures.add(executor.submit(() -> renderSample(canvas, world, picker)));
            }
            for (Future<?> future : futures) {
                future.get();
            }
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
