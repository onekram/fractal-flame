package backend.academy.render.renderer;

import backend.academy.render.FractalImage;
import backend.academy.render.shapes.Point;
import backend.academy.render.shapes.Rect;
import backend.academy.transformation.LinearTransformation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class SingleThreadRendererTest extends AbstractRendererTest{
    @Override
    protected Renderer getRenderer() {
        return new SingleThreadRenderer();
    }
}
