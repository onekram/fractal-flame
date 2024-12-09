package backend.academy.argparser;

import backend.academy.display.ImageFormat;
import com.beust.jcommander.JCommander;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
        String[] args = {"-w", "100", "-h", "100", "-i", "100", "-c", tempFile.toString(), "-f", "BMP"};

        assertDoesNotThrow(() -> parser.parse(args));
        assertThat(parsedArgs.width()).isEqualTo(100);
        assertThat(parsedArgs.height()).isEqualTo(100);
        assertThat(parsedArgs.iterations()).isEqualTo(100);
        assertThat(parsedArgs.config()).isEqualTo(tempFile);
        assertThat(parsedArgs.format()).isEqualTo(ImageFormat.BMP);
    }
}
