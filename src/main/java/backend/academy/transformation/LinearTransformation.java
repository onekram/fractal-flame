package backend.academy.transformation;

import backend.academy.render.shapes.Point;
import backend.academy.utils.Probabilistic;
import backend.academy.utils.RandomUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import lombok.Getter;

@Getter
public class LinearTransformation implements Transformation, Probabilistic {
    private static final int COLOR_BOUND = 255;
    public static final double COEFFICIENT_BOUND = 1;
    public static final int COEFFICIENTS_COUNT = 6;

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

    public static LinearTransformation fromNonlinearTransformations(List<NonlinearTransformation> transformations) {
        LinearTransformation linearTransformation = new LinearTransformation();
        linearTransformation.probability = RandomUtils.nextDouble();
        linearTransformation.red = RandomUtils.nextIntBetween(0, COLOR_BOUND);
        linearTransformation.blue = RandomUtils.nextIntBetween(0, COLOR_BOUND);
        linearTransformation.green = RandomUtils.nextIntBetween(0, COLOR_BOUND);

        List<Double> weights = Stream.generate(RandomUtils::nextDouble).limit(transformations.size()).toList();
        double total = weights.stream().mapToDouble(Double::doubleValue).sum();
        linearTransformation.variations = IntStream.range(0, transformations.size())
            .mapToObj(i -> new Variation(weights.get(i) / total, transformations.get(i))).toList();

        linearTransformation.coefficients =
            Stream.generate(() -> RandomUtils.nextDoubleBetween(-COEFFICIENT_BOUND, COEFFICIENT_BOUND)).limit(
                    COEFFICIENTS_COUNT)
                .toList();
        return linearTransformation;
    }
}
