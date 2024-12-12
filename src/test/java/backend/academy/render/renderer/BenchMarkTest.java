package backend.academy.render.renderer;

import backend.academy.render.FractalImage;
import backend.academy.render.shapes.Point;
import backend.academy.render.shapes.Rect;
import backend.academy.transformation.LinearTransformation;
import backend.academy.transformation.NonlinearTransformation;
import backend.academy.transformation.Transformation;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BenchMarkTest {
    @State(Scope.Benchmark)
    public static class BenchmarkState {
        LinearTransformation lt1 = mock(LinearTransformation.class);
        LinearTransformation lt2 = mock(LinearTransformation.class);
        FractalImage image;
        Rect rect;
        Renderer single;
        Renderer executorService;
        Renderer forkJoinPool;

        @Setup()
        public void setUp() {
            when(lt1.apply(any())).thenAnswer(invocationOnMock -> {
                Point p = invocationOnMock.getArgument(0);
                var func = Transformation.fromCoefficients(List.of(0.289, 0.196, -0.777, 1.200, 0.668, 1.242)).andThen(
                    NonlinearTransformation.SINUSOIDAL);
                return func.apply(p);
            });
            when(lt1.red()).thenReturn(255);
            when(lt1.blue()).thenReturn(0);
            when(lt1.green()).thenReturn(255);
            when(lt1.getProbability()).thenReturn(0.6);

            when(lt2.apply(any())).thenAnswer(invocationOnMock -> {
                Point p = invocationOnMock.getArgument(0);
                var func = Transformation.fromCoefficients(List.of(0.401, -0.490, 1.089, 0.873, 0.429, -0.275)).andThen(
                    NonlinearTransformation.HEART);
                return func.apply(p);
            });
            when(lt2.red()).thenReturn(0);
            when(lt2.blue()).thenReturn(200);
            when(lt2.green()).thenReturn(100);
            when(lt2.getProbability()).thenReturn(0.4);
            image = FractalImage.create(1000, 1000);
            rect = Rect.getMirror(1, 1);
            single = new SingleThreadRenderer(List.of(lt1, lt2), 5, 3000, 10, false, false);
            executorService = new ExecutorServiceFractalRenderer(List.of(lt1, lt2), 5, 3000, 10, false, false);
            forkJoinPool = new ForkJoinPoolFractalRenderer(List.of(lt1, lt2), 5, 3000, 10, false, false);
        }
    }

    @Benchmark
    public void singleThread(BenchmarkState state) {
        state.single.render(state.image, state.rect);
    }

    @Benchmark
    public void executorService(BenchmarkState state) {
        state.executorService.render(state.image, state.rect);
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
            .measurementTime(TimeValue.seconds(1))
            .measurementIterations(3)
            .forks(1)
            .shouldFailOnError(true)
            .build();

        new Runner(opt).run();
    }
}
