package backend.academy.report.statistics;

import backend.academy.argparser.Args;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ArgumentParametersStatistics implements Statistics {
    private final Args parsedArgs;

    @Override
    public String getName() {
        return "Argument parameters";
    }

    @Override
    public List<String> getTable() {
        LinkedList<String> table = new LinkedList<>(List.of("Parameter | Value"));
        Arrays.stream(parsedArgs.getClass().getDeclaredFields())
            .peek(field -> field.setAccessible(true))
            .filter(field -> !Modifier.isFinal(field.getModifiers()))
            .map(field -> {
                try {
                    Object value = field.get(parsedArgs);
                    return String.format("%s | %s", field.getName(), value == null ? "not provided" : value.toString());
                } catch (IllegalAccessException e) {
                    throw new IllegalStateException(e);
                }
            })
            .forEach(table::add);
        return table;
    }
}
