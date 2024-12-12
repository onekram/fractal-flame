package backend.academy.argparser;

import backend.academy.report.AdocReportWriter;
import backend.academy.report.MDReportWriter;
import backend.academy.report.ReportWriter;
import java.io.Writer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReportFormat {
    MARKDOWN {
        @Override
        public ReportWriter getReportWriter(Writer writer) {
            return new MDReportWriter(writer);
        }
    },
    ADOC {
        @Override
        public ReportWriter getReportWriter(Writer writer) {
            return new AdocReportWriter(writer);
        }
    };

    public abstract ReportWriter getReportWriter(Writer writer);

    public static ReportFormat fromString(String s) {
        for (ReportFormat format : ReportFormat.values()) {
            if (format.name().equalsIgnoreCase(s)) {
                return format;
            }
        }
        throw new IllegalArgumentException("Unknown format: " + s);
    }
}
