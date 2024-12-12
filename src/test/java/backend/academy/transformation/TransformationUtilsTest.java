package backend.academy.transformation;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TransformationUtilsTest {

    @Test
    @DisplayName("Correctness test")
    void generateTransformations() {
        List<NonlinearTransformation> transformations = List.of(
            NonlinearTransformation.SINUSOIDAL,
            NonlinearTransformation.FISHEYE,
            NonlinearTransformation.BUBBLE
        );
        List<LinearTransformation> lts = TransformationUtils.generateTransformations(transformations, 100);
        assertThat(lts).isNotNull();
        assertThat(lts.size()).isGreaterThanOrEqualTo(50).isLessThanOrEqualTo(100);
        for (LinearTransformation lt : lts) {
            for (int i = 0; i < lt.variations().size(); i++) {
                assertThat(lt.variations().get(i).func()).isEqualTo(transformations.get(i));
            }
        }
    }
}
