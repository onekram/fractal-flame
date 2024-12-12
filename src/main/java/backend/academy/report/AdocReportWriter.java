package backend.academy.report;

import java.io.Writer;
import java.util.List;

public class AdocReportWriter extends ReportWriter {

    public AdocReportWriter(Writer writer) {
        super(writer);
    }

    @Override
    protected void writeHeader(String name) {
        writer.printf("==== %s%n", name);
    }

    @Override
    protected void writeTable(List<String> table) {
        String tableBound = "|===";
        writer.println(tableBound);
        for (String row : table) {
            writer.println("| " + row);
        }
        writer.println(tableBound);
    }
}
