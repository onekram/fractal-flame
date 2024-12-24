package backend.academy.render.renderer;

import backend.academy.render.FractalImage;
import backend.academy.render.shapes.Rect;
import backend.academy.transformation.LinearTransformation;
import backend.academy.transformation.NonlinearTransformation;
import backend.academy.transformation.TransformationUtils;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

public class BenchMarkTest {
    @State(Scope.Benchmark)
    public static class BenchmarkState {
        List<LinearTransformation> transformations;
        FractalImage image;
        Rect rect;
        Renderer single;
        Renderer cachedThreadExecutorService;
        Renderer virtualThreadExecutorService;
        Renderer forkJoinPool;

        @Setup()
        public void setUp() {
            transformations = TransformationUtils.generateTransformations(
                List.of(NonlinearTransformation.SINUSOIDAL, NonlinearTransformation.HORSESHOE), 2);
            image = FractalImage.create(1000, 1000);
            rect = Rect.getMirror(1, 1);
            int samples = 20;
            int iterPerSample = 20000;
            int rotations = 10;
            single = new SingleThreadRenderer(transformations, samples, iterPerSample, rotations, false, false);
            cachedThreadExecutorService =
                new CachedThreadExecutorServiceFractalRenderer(transformations, samples, iterPerSample, rotations,
                    false, false);
            virtualThreadExecutorService =
                new VirtualThreadExecutorServiceFractalRenderer(transformations, samples, iterPerSample, rotations,
                    false, false);
            forkJoinPool =
                new ForkJoinPoolFractalRenderer(transformations, samples, iterPerSample, rotations, false, false);
        }
    }

    @Benchmark
    public void singleThread(BenchmarkState state) {
        state.single.render(state.image, state.rect);
    }

    @Benchmark
    public void cachedThreadExecutorService(BenchmarkState state) {
        state.cachedThreadExecutorService.render(state.image, state.rect);
    }

    @Benchmark
    public void virtualThreadExecutorService(BenchmarkState state) {
        state.virtualThreadExecutorService.render(state.image, state.rect);
    }

    @Benchmark
    public void forkJointPool(BenchmarkState state) {
        state.forkJoinPool.render(state.image, state.rect);
    }

    @Test
    public void launchBenchmark() throws RunnerException {
        Options opt = new OptionsBuilder()
            .include(this.getClass().getName() + ".*")
            .mode(Mode.AverageTime)
            .timeUnit(TimeUnit.MICROSECONDS)
            .warmupTime(TimeValue.seconds(1))
            .warmupIterations(3)
            .measurementTime(TimeValue.seconds(3))
            .measurementIterations(3)
            .forks(1)
            .shouldFailOnError(true)
            .build();

        new Runner(opt).run();
    }
}
