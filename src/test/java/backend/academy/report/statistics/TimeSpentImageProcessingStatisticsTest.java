package backend.academy.report.statistics;

import backend.academy.report.AdocReportWriter;
import java.io.StringWriter;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TimeSpentImageProcessingStatisticsTest {
    @Test
    @DisplayName("Correctness test")
    void correctness() {
        Statistics st = new TimeSpentImageProcessingStatistics(12567);
        StringWriter sw = new StringWriter();
        new AdocReportWriter(sw).write(List.of(st));

        assertThat(sw.toString()).isEqualTo("""
            ==== Time spent on image conversion
            |===
            | Time
            | 12.567 seconds
            |===
            """);
    }
}
