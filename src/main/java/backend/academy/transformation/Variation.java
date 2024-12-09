package backend.academy.transformation;

import backend.academy.render.shapes.Point;
import com.fasterxml.jackson.annotation.JsonProperty;

public record Variation(@JsonProperty("weight") double weight,
                        @JsonProperty("func") NonlinearTransformation func)
    implements Transformation {
    @Override
    public Point apply(Point point) {
        return func.andThen(p -> new Point(p.x() * weight, p.y() * weight)).apply(point);
    }
}
