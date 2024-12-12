package backend.academy.report;

import backend.academy.report.statistics.Statistics;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

public abstract class ReportWriter {
    protected final PrintWriter writer;

    protected ReportWriter(Writer writer) {
        this.writer = new PrintWriter(writer);
    }

    public void write(List<Statistics> statistics) {
        statistics.forEach(this::writeStatistic);
        writer.flush();
    }

    protected abstract void writeHeader(String header);

    protected abstract void writeTable(List<String> table);

    private void writeStatistic(Statistics statistics) {
        writeHeader(statistics.getName());
        writeTable(statistics.getTable());
    }
}
