package backend.academy.argparser;

import com.beust.jcommander.IStringConverter;

public class NonlinearTransformationConverter implements IStringConverter<NonlinearTransformation> {
    @Override
    public NonlinearTransformation convert(String s) {
        return NonlinearTransformation.fromString(s);
    }
}
