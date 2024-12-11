package backend.academy.argparser;

import backend.academy.render.renderer.ExecutorServiceFractalRenderer;
import backend.academy.render.renderer.Renderer;
import backend.academy.render.renderer.SingleThreadRenderer;

public enum ThreadRendererType {
    SINGLE {
        @Override
        public Renderer getRenderer() {
            return new SingleThreadRenderer();
        }
    },
    PARALLEL {
        @Override
        public Renderer getRenderer() {
            return new ExecutorServiceFractalRenderer();
        }
    };

    public abstract Renderer getRenderer();

    public static ThreadRendererType fromString(String s) {
        for (ThreadRendererType type : ThreadRendererType.values()) {
            if (type.name().equalsIgnoreCase(s)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown thread type: " + s);
    }
}
