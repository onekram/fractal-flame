package backend.academy.argparser;

import backend.academy.display.ImageFormat;
import com.beust.jcommander.Parameter;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        names = {"-r", "--rotations"},
        description = "Number of rotations for generation",
        defaultValueDescription = "Set 1 as default"
    )
    private int rotations;

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

    @Parameter(
        names = {"-o", "--output"},
        description = "Output image file",
        defaultValueDescription = "out.jpg file",
        converter = OutputPathConverter.class
    )
    private Path output = Paths.get("out.jpg");

    @Parameter(
        names = {"-z", "--zoom"},
        description = "Zoom for output image",
        defaultValueDescription = "Set 1 as default"
    )
    private double zoom = 1;

    @Parameter(
        names = {"-s", "--show"},
        description = "Show fractal in window",
        defaultValueDescription = "Turned off"
    )
    private boolean show = false;

    @Parameter(names = {"--h", "--help"}, help = true)
    private boolean help;
}
