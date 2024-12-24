package backend.academy.argparser;

import backend.academy.display.ImageFormat;
import backend.academy.transformation.NonlinearTransformation;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ArgParserTest {
    private static JCommander parser;
    private static Args parsedArgs;
    private static Path tempFile;

    @BeforeAll
    public static void setUpAll() throws IOException {
        tempFile = Files.createTempFile("config", ".json");
    }

    @AfterAll
    public static void tearDownAll() throws IOException {
        Files.deleteIfExists(tempFile);
    }

    @BeforeEach
    public void setUp() {
        parsedArgs = new Args();
        parser = JCommander.newBuilder()
            .addObject(parsedArgs)
            .build();
    }

    @Test
    @DisplayName("Correctness test")
    void correctness() {
        String[] args =
            {
                "-w", "100",
                "-h", "100",
                "-i", "100",
                "-c", tempFile.toString(),
                "-f", "BmP",
                "-o", "output.file",
                "--thread-type", "sinGlE",
                "--report-format", "aDoC",
                "--report-output", "report.adoc",
                "-sx",
                "-sy",
                "-g", "0.4"};

        assertDoesNotThrow(() -> parser.parse(args));
        assertThat(parsedArgs.width()).isEqualTo(100);
        assertThat(parsedArgs.height()).isEqualTo(100);
        assertThat(parsedArgs.iterations()).isEqualTo(100);
        assertThat(parsedArgs.config()).isEqualTo(tempFile);
        assertThat(parsedArgs.format()).isEqualTo(ImageFormat.BMP);
        assertThat(parsedArgs.output()).isEqualTo(Path.of("output.file"));
        assertThat(parsedArgs.threadType()).isEqualTo(ThreadRendererType.SINGLE);
        assertThat(parsedArgs.reportFormat()).isEqualTo(ReportFormat.ADOC);
        assertThat(parsedArgs.reportOutput()).isEqualTo(Path.of("report.adoc"));
        assertThat(parsedArgs.symmetricX()).isTrue();
        assertThat(parsedArgs.symmetricY()).isTrue();
        assertThat(parsedArgs.gamma()).isEqualTo(0.4);
    }

    @Test
    @DisplayName("Build from transformations")
    void transformation() {
        String[] args = {"-w", "100", "-h", "100", "-i", "100",
            "--transformations",
            NonlinearTransformation.SINUSOIDAL.toString(),
            NonlinearTransformation.HEART.toString(),
            NonlinearTransformation.SPHERICAL.toString()};

        assertDoesNotThrow(() -> parser.parse(args));
        assertThat(parsedArgs.transformations()).isEqualTo(
            List.of(
                NonlinearTransformation.SINUSOIDAL,
                NonlinearTransformation.HEART,
                NonlinearTransformation.SPHERICAL
            ));
    }

    @Test
    @DisplayName("No config validation test")
    void validate() {
        String[] args = {"-w", "100", "-h", "100", "-i", "100", "-f", "BMP"};
        assertThatThrownBy(() -> parser.parse(args)).isInstanceOf(ParameterException.class)
            .hasMessageContaining("--config or --transformations are required");
    }

    @Test
    @DisplayName("Config file is not exist")
    void wrongConfigFile() {
        String[] args = {"-w", "100", "-h", "100", "-i", "100", "-f", "BMP", "-c", "abd.def"};
        assertThatThrownBy(() -> parser.parse(args)).isInstanceOf(ParameterException.class)
            .hasMessageContaining("File abd.def does not exist");
    }

    @Test
    @DisplayName("Only positive values are suitable")
    void validatePositive() {
        String[] args = {"-w", "-100", "-h", "100", "-i", "100", "--transformations", "spherical"};
        assertThatThrownBy(() -> parser.parse(args)).isInstanceOf(ParameterException.class)
            .hasMessageContaining("Parameter -w should be positive");

        String[] args1 = {"-w", "100", "-h", "100", "-i", "100", "--transformations", "spherical", "--gamma", "-0.5"};
        assertThatThrownBy(() -> parser.parse(args1)).isInstanceOf(ParameterException.class)
            .hasMessageContaining("Parameter --gamma should be positive");
    }
}
