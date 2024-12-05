package backend.academy.argparser;

import com.beust.jcommander.IStringConverter;

public class ImageFormatConverter implements IStringConverter<ImageFormat> {
    @Override
    public ImageFormat convert(String s) {
        return ImageFormat.fromString(s);
    }
}
