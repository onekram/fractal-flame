package backend.academy.render.renderer;

public class ForkJoinPoolRendererTest extends AbstractRendererTest {
    @Override
    protected Renderer getRenderer() {
        return new ForkJoinPoolFractalRenderer();
    }
}
