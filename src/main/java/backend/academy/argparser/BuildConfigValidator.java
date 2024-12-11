package backend.academy.argparser;

import com.beust.jcommander.IParametersValidator;
import com.beust.jcommander.ParameterException;
import java.util.Map;

public class BuildConfigValidator implements IParametersValidator {
    @Override
    public void validate(Map<String, Object> map) throws ParameterException {
        if (map.get("--config") == null && map.get("--transformations") == null) {
            throw new ParameterException("--config or --transformations are required");
        }
    }
}
