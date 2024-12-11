package backend.academy.render.renderer;

import backend.academy.render.FractalImage;
import backend.academy.render.shapes.Rect;

public interface Renderer {
    void render(
        FractalImage canvas,
        Rect world
    );
}
