package backend.academy.argparser;

public enum ImageFormat {
    JPG,
    BMP,
    PNG;

    public static ImageFormat fromString(String s) {
        for (ImageFormat format : ImageFormat.values()) {
            if (format.name().equalsIgnoreCase(s)) {
                return format;
            }
        }
        throw new IllegalArgumentException("Unknown format: " + s);
    }
}
