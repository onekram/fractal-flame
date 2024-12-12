package backend.academy.argparser;

import backend.academy.display.ImageFormat;
import backend.academy.transformation.NonlinearTransformation;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Parameters(parametersValidators = {BuildConfigValidator.class})
public class Args {
    private static final double DEFAULT_GAMMA = 0.5;
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
    private int rotations = 1;

    @Parameter(
        names = {"-c", "--config"},
        description = "Transformations for generation pass via json config",
        validateWith = PathValidator.class
    )
    private Path config;

    @Parameter(
        names = {"-f", "--format"},
        description = "Output image format",
        defaultValueDescription = "JPG image format"
    )
    private ImageFormat format = ImageFormat.JPG;

    @Parameter(
        names = {"-o", "--output"},
        description = "Output image file",
        defaultValueDescription = "out.jpg file"
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

    @Parameter(
        names = {"--thread-type"},
        description = "Specify render parallel or in single thread",
        defaultValueDescription = "Parallel"
    )
    private ThreadRendererType threadType = ThreadRendererType.PARALLEL;

    @Parameter(
        names = {"-g", "--gamma"},
        description = "Gamma correction coefficient should be less or equal than 1",
        defaultValueDescription = "Set 0.5 as default"
    )
    private double gamma = DEFAULT_GAMMA;

    @Parameter(
        names = {"--transformations"},
        description = "Transformations on the basis of which the configuration will be built "
            + "(ignore if config file passed)",
        variableArity = true
    )
    private List<NonlinearTransformation> transformations;

    @Parameter(
        names = {"-sx", "--symmetric-x"},
        description = "Symmetrical about the abscissa axis",
        defaultValueDescription = "Turned off"
    )
    private boolean symmetricX = false;

    @Parameter(
        names = {"-sy", "--symmetric-y"},
        description = "Symmetrical about the ordinate axis",
        defaultValueDescription = "Turned off"
    )
    private boolean symmetricY = false;

    @Parameter(
        names = {"--report-format"},
        description = "Report format",
        defaultValueDescription = "Markdown"
    )
    private ReportFormat reportFormat = ReportFormat.MARKDOWN;

    @Parameter(
        names = {"--report-output"},
        description = "Destination for report output",
        defaultValueDescription = "Standard output"
    )
    private Path reportOutput = Paths.get("report.md");

    @Parameter(names = {"--h", "--help"}, help = true)
    private boolean help;
}
