package backend.academy.argparser;

import com.beust.jcommander.Parameter;
import java.nio.file.Path;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Args {
    @Parameter(
        names = {"-w", "--width"},
        description = "Generated image width",
        required = true
    )
    private int width;

    @Parameter(
        names = {"-h", "--height"},
        description = "Generated image height",
        required = true
    )
    private int height;

    @Parameter(
        names = {"-i", "--iterations"},
        description = "Number of iterations for generation",
        required = true
    )
    private int iterations;

    @Parameter(
        names = {"-c", "--config"},
        description = "Transformations for generation pass via json config",
        converter = PathConverter.class,
        required = true
    )
    private Path config;

    @Parameter(
        names = {"-f", "--format"},
        description = "Output image format",
        defaultValueDescription = "JPG image format",
        converter = ImageFormatConverter.class
    )
    private ImageFormat format = ImageFormat.JPG;

    @Parameter(names = {"--h", "--help"}, help = true)
    private boolean help;
}
