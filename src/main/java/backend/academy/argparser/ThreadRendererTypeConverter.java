package backend.academy.argparser;

import com.beust.jcommander.IStringConverter;

public class ThreadRendererTypeConverter implements IStringConverter<ThreadRendererType> {
    @Override
    public ThreadRendererType convert(String s) {
        return ThreadRendererType.fromString(s);
    }
}
