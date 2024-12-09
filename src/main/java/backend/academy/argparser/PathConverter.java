package backend.academy.argparser;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.ParameterException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.io.FilenameUtils;

public class PathConverter implements IStringConverter<Path> {
    @Override
    public Path convert(String s) {
        Path path = Paths.get(FilenameUtils.getName(s));
        if (!Files.exists(path)) {
            throw new ParameterException("File " + s + " does not exist");
        }
        return path;
    }
}
