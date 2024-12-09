package backend.academy;

import backend.academy.configparser.ConfigParser;
import backend.academy.shapes.Point;
import backend.academy.transformation.LinearTransformation;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ConfigParserTest {

    private Path tempFile;

    @BeforeEach
    public void setUp() throws IOException {
        tempFile = Files.createTempFile("config", ".json");
        Files.writeString(tempFile, """
            [
                {
                    "probability": 0.416,
                    "red": 255,
                    "green": 255,
                    "blue": 255,
                    "coefficients": [
                        1,
                        2,
                        3,
                        4,
                        5,
                        6
                    ],
                    "variations": [
                        {
                            "weight": 1,
                            "func": "SINUSOIDAL"
                        },
                        {
                            "weight": 10,
                            "func": "SPHERICAL"
                        }
                    ]
                }
            ]
            """);
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(tempFile);
    }

    @Test
    @DisplayName("No error parse")
    void noErrorParse() {
        assertDoesNotThrow(() -> ConfigParser.parse(tempFile.toString()));
    }

    @Test
    @DisplayName("Correct parse")
    void correctParse() throws IOException {
        LinearTransformation transformation = ConfigParser.parse(tempFile.toString()).getFirst();
        assertThat(transformation.getProbability()).isEqualTo(0.416);
        assertThat(transformation.red()).isEqualTo(255);
        assertThat(transformation.green()).isEqualTo(255);
        assertThat(transformation.blue()).isEqualTo(255);

        Point p = transformation.apply(new Point(10, 15));
        assertThat(p.x()).isCloseTo(-0.805, Percentage.withPercentage(1));
        assertThat(p.y()).isCloseTo(1.024, Percentage.withPercentage(1));
    }
}
