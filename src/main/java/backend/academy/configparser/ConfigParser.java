package backend.academy.configparser;

import backend.academy.transformation.LinearTransformation;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

@Log4j2
@UtilityClass
public class ConfigParser {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static List<LinearTransformation> parse(String filename) throws IOException {
        LinearTransformation[] transformations =
            OBJECT_MAPPER.readValue(new File(filename), LinearTransformation[].class);
        return new ArrayList<>(List.of(transformations));
    }
}
