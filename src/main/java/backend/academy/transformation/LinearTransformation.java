package backend.academy.transformation;

import backend.academy.render.shapes.Point;
import backend.academy.utils.Probabilistic;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.function.Function;
import lombok.Getter;

public class LinearTransformation implements Transformation, Probabilistic {

    @JsonProperty("probability")
    private double probability;

    @Getter
    @JsonProperty("red")
    private int red;

    @Getter
    @JsonProperty("green")
    private int green;

    @Getter
    @JsonProperty("blue")
    private int blue;

    @JsonProperty("coefficients")
    private List<Double> coefficients;

    @JsonProperty("variations")
    private List<Variation> variations;

    @Override
    public Point apply(Point point) {
        Function<Point, Point> func;
        if (variations.isEmpty()) {
            func = Function.identity();
        } else {
            func = input -> variations.stream()
                .reduce(new Point(0, 0), (acc, v) -> acc.add(v.apply(input)), Point::add);
        }

        return Transformation.fromCoefficients(coefficients).andThen(func).apply(point);
    }

    @Override
    public double getProbability() {
        return probability;
    }
}
