package backend.academy.argparser;

import com.beust.jcommander.IStringConverter;

public class ReportFormatConverter implements IStringConverter<ReportFormat> {
    @Override
    public ReportFormat convert(String s) {
        return ReportFormat.fromString(s);
    }
}
