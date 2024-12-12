package backend.academy.argparser;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PathValidator implements IParameterValidator {
    @Override
    public void validate(String name, String value) throws ParameterException {
        if (!Files.exists(Path.of(value))) {
            throw new ParameterException("File " + value + " does not exist");
        }
    }
}
