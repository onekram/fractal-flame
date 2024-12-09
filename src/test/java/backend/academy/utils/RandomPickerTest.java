package backend.academy.utils;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RandomPickerTest {
    @Test
    @DisplayName("One element")
    void oneElementTest() {
        Probabilistic obj = () -> 100;
        RandomPicker picker = new RandomPicker(List.of(obj));

        assertThat(picker.pick()).isEqualTo(obj);
    }

    @Test
    @DisplayName("No elements")
    void noElementsTest() {
        assertThatThrownBy(() -> {
            new RandomPicker(Collections.emptyList());
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Correct probability")
    void correctProbabilityTest() {
        var ps = IntStream.range(0, 10)
            .mapToObj(it -> (Probabilistic) () -> it)
            .toList();
        int total = 45;
        int trials = 1000000;
        double[] hits = new double[ps.size()];
        RandomPicker picker = new RandomPicker(ps);
        for (int i = 0; i < trials; i++) {
            hits[(int) picker.pick().getProbability()]++;
        }

        for (int i = 0; i < ps.size(); i++) {
            assertThat(hits[i] / trials)
                .isCloseTo(ps.get(i).getProbability() / total, Percentage.withPercentage(5));
        }
    }
}
