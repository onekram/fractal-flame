package backend.academy.transformation;

import backend.academy.utils.RandomUtils;
import java.util.List;
import java.util.stream.Stream;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TransformationUtils {
    public static List<LinearTransformation> generateTransformations(
        List<NonlinearTransformation> transformations,
        int count
    ) {
        return Stream.generate(() -> LinearTransformation.fromNonlinearTransformations(transformations))
            .limit(RandomUtils.nextIntBetween(count / 2, count)).toList();
    }
}
