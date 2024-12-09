package backend.academy.render;

import java.util.Arrays;

public record FractalImage(Pixel[] data, int width, int height) {
    public static FractalImage create(int width, int height) {
        Pixel[] pixels = new Pixel[width * height];
        Arrays.setAll(pixels, i -> new Pixel(0, 0, 0, 0));
        return new FractalImage(pixels, width, height);
    }

    public Pixel pixel(int x, int y) {
        return data[x + y * width];
    }
}
