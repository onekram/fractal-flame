package backend.academy.argparser;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ImageFormat {
    JPG("jpg"),
    BMP("bmp"),
    PNG("png");

    private final String formatName;

    public void writeImage(BufferedImage image, Path path) {
        try {
            ImageIO.write(image, formatName, path.toFile());
        } catch (IOException e) {
            throw new RuntimeException("Fail to write image to " + path, e);
        }
    }

    public static ImageFormat fromString(String s) {
        for (ImageFormat format : ImageFormat.values()) {
            if (format.name().equalsIgnoreCase(s)) {
                return format;
            }
        }
        throw new IllegalArgumentException("Unknown format: " + s);
    }
}
