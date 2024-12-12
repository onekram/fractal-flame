package backend.academy.report;

import java.io.Writer;
import java.util.Collections;
import java.util.List;

public class MDReportWriter extends ReportWriter {

    public MDReportWriter(Writer writer) {
        super(writer);
    }

    @Override
    protected void writeHeader(String name) {
        writer.printf("#### %s%n", name);
    }

    @Override
    protected void writeTable(List<String> table) {
        writer.println("| " + table.getFirst());
        int count = table.getFirst().split("\\|").length;
        writer.println("|" + String.join("|", Collections.nCopies(count, ":---")) + "|");
        for (String row : table.subList(1, table.size())) {
            writer.println("| " + row + " |");
        }
    }
}
