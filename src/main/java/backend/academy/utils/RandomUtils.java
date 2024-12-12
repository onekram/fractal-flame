package backend.academy.utils;

import java.util.concurrent.ThreadLocalRandom;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RandomUtils {
    public static double nextDoubleBetween(double min, double max) {
        return min + (max - min) * ThreadLocalRandom.current().nextDouble();
    }

    public static double nextDouble() {
        return ThreadLocalRandom.current().nextDouble();
    }

    public static int nextIntBetween(int min, int max) {
        return ThreadLocalRandom.current().nextInt(max - min + 1) + min;
    }
}
