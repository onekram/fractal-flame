package backend.academy.utils;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomPicker<T extends Probabilistic> {
    private final double[] cumulativeProbabilities;
    private final List<T> items;
    private final static Random RANDOM = new SecureRandom();

    public RandomPicker(List<T> list) {
        if (list.isEmpty()) {
            throw new IllegalArgumentException("List is empty");
        }
        this.items = list;
        double total = list.stream().mapToDouble(Probabilistic::getProbability).sum();
        cumulativeProbabilities =
            list.stream().mapToDouble(Probabilistic::getProbability).map(v -> v / total).toArray();
        for (int i = 1; i < cumulativeProbabilities.length; i++) {
            cumulativeProbabilities[i] += cumulativeProbabilities[i - 1];
        }
    }

    public T pick() {
        double randomValue = RANDOM.nextDouble();
        int index = Arrays.binarySearch(cumulativeProbabilities, randomValue);

        if (index < 0) {
            index = -(index + 1);
        }

        return items.get(index);
    }
}
