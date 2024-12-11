package backend.academy.argparser;

import com.beust.jcommander.IStringConverter;
import java.nio.file.Path;

public class OutputPathConverter implements IStringConverter<Path> {
    @Override
    public Path convert(String s) {
        return Path.of(s);
    }
}
