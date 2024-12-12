package backend.academy.report.statistics;

import backend.academy.transformation.LinearTransformation;
import java.util.LinkedList;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TransformationStatistics implements Statistics {
    private final List<LinearTransformation> transformations;

    @Override
    public String getName() {
        return "Functions used for transformation";
    }

    @Override
    public List<String> getTable() {
        List<String> table = new LinkedList<>(List.of("Field | Value"));
        for (int i = 0; i < transformations.size(); i++) {
            LinearTransformation transformation = transformations.get(i);
            table.add(String.format("Probability | %.3f", transformation.probability()));
            table.add(
                String.format("Color | Red: %d, Green: %d, Blue: %d",
                    transformation.red(),
                    transformation.green(),
                    transformation.blue()));
            table.add(String.format("Linear coefficients | %s", transformation.coefficients()
                .stream().map(d -> String.format("%.3f", d)).toList()));
            table.add(String.format("Variations | %s", transformation.variations().stream()
                .map(variation -> String.format("Weight: %.3f, Func: %s", variation.weight(), variation.func()))
                .toList()));
            if (i < transformations.size() - 1) {
                table.add(" | ");
            }
        }
        return table;
    }
}
