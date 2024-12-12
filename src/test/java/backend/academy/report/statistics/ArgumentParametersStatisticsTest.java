package backend.academy.report.statistics;

import backend.academy.argparser.Args;
import backend.academy.argparser.ReportFormat;
import backend.academy.argparser.ThreadRendererType;
import backend.academy.display.ImageFormat;
import backend.academy.report.AdocReportWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ArgumentParametersStatisticsTest {

    private static void setField(Object obj, String fieldName, Object value)
        throws NoSuchFieldException, IllegalAccessException {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(obj, value);
    }

    @Test
    @DisplayName("Correct table")
    void correctness() throws NoSuchFieldException, IllegalAccessException {
        Args args = new Args();
        setField(args, "width", 100);
        setField(args, "height", 100);
        setField(args, "iterations", 100);
        setField(args, "rotations", 100);
        setField(args, "config", Path.of("config.file"));
        setField(args, "format", ImageFormat.PNG);
        setField(args, "output", Path.of("out.png"));
        setField(args, "zoom", 0.3);
        setField(args, "show", false);
        setField(args, "threadType", ThreadRendererType.SINGLE);
        setField(args, "gamma", 0.4);
        setField(args, "transformations", null);
        setField(args, "symmetricX", true);
        setField(args, "symmetricY", false);
        setField(args, "reportFormat", ReportFormat.MARKDOWN);
        setField(args, "reportOutput", Path.of("report.md"));
        setField(args, "help", false);

        Statistics st = new ArgumentParametersStatistics(args);
        StringWriter sw = new StringWriter();
        new AdocReportWriter(sw).write(List.of(st));

        assertThat(sw.toString()).isEqualTo("""
            ==== Argument parameters
            |===
            | Parameter | Value
            | width | 100
            | height | 100
            | iterations | 100
            | rotations | 100
            | config | config.file
            | format | PNG
            | output | out.png
            | zoom | 0.3
            | show | false
            | threadType | SINGLE
            | gamma | 0.4
            | transformations | not provided
            | symmetricX | true
            | symmetricY | false
            | reportFormat | MARKDOWN
            | reportOutput | report.md
            | help | false
            |===
            """);
    }
}
