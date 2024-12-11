package backend.academy.argparser;

import backend.academy.transformation.NonlinearTransformation;
import com.beust.jcommander.IStringConverter;

public class NonLinearTransformationConverter implements IStringConverter<NonlinearTransformation> {
    @Override
    public NonlinearTransformation convert(String s) {
        return NonlinearTransformation.fromString(s);
    }
}
