package backend.academy.utils;

import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class RandomUtilsTest {

    @RepeatedTest(100)
    @DisplayName("Correctness test for random double between bounds")
    void correctness() {
        assertThat(RandomUtils.nextDoubleBetween(0.0, 0.0)).isEqualTo(0.0);
        assertThat(RandomUtils.nextDoubleBetween(-0.0, 0.0)).isEqualTo(0.0);
        assertThat(RandomUtils.nextDoubleBetween(0.0, 0.0001)).isBetween(0.0, 0.0001);
        assertThat(RandomUtils.nextDoubleBetween(-5, 0.0)).isBetween(-5.0, 0.0);
        assertThat(RandomUtils.nextDoubleBetween(-1, -0.99999)).isBetween(-1.0, -0.99999);
    }

    @Test
    @DisplayName("Correct distribution test")
    void correctDistribution() {
        int[] hits = new int[10];
        for (int i = 0; i < 10000000; i++) {
            double r = RandomUtils.nextDoubleBetween(0, 10);
            hits[(int) r]++;
        }
        Arrays.stream(hits).forEach(i -> assertThat(i).isCloseTo(hits[0], Percentage.withPercentage(2)));
    }
}
