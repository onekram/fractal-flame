package backend.academy.report;

import backend.academy.report.statistics.Statistics;
import java.io.StringWriter;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ReportWriterTest {
    @Test
    @DisplayName("Correctness test ADOC")
    void adoc() {
        Statistics st = new Statistics() {
            @Override
            public String getName() {
                return "Statistic name";
            }

            @Override
            public List<String> getTable() {
                return List.of("Field | Value", "Count | 20", "File | file.file");
            }
        };
        StringWriter sw = new StringWriter();
        new AdocReportWriter(sw).write(List.of(st));
        assertThat(sw.toString()).isEqualTo("""
            ==== Statistic name
            |===
            | Field | Value
            | Count | 20
            | File | file.file
            |===
            """);
    }

    @Test
    @DisplayName("Correctness test ADOC")
    void md() {
        Statistics st = new Statistics() {
            @Override
            public String getName() {
                return "Statistic name";
            }

            @Override
            public List<String> getTable() {
                return List.of("Field | Value", "Count | 20", "File | file.file");
            }
        };
        StringWriter sw = new StringWriter();
        new MDReportWriter(sw).write(List.of(st));
        assertThat(sw.toString()).isEqualTo("""
            #### Statistic name
            | Field | Value
            |:---|:---|
            | Count | 20 |
            | File | file.file |
            """);
    }

}
