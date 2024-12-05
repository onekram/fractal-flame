package backend.academy.argparser;

import com.beust.jcommander.Parameter;
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
        names = {"-f", "--format"},
        description = "Output image format",
        defaultValueDescription = "JPG image format"
    )
    private ImageFormat format = ImageFormat.JPG;

    @Parameter(names = {"--h", "--help"}, help = true)
    private boolean help;
}
