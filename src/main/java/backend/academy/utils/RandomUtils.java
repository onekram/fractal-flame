package backend.academy.utils;

import lombok.experimental.UtilityClass;
import java.security.SecureRandom;
import java.util.Random;

@UtilityClass
public class RandomUtils {
    private static final Random RANDOM = new SecureRandom();
    public static double nextDoubleBetween(double min, double max) {
        return min + (max - min) * RANDOM.nextDouble();
    }
}
