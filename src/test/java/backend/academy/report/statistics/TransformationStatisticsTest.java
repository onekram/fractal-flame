package backend.academy.report.statistics;

import backend.academy.report.AdocReportWriter;
import backend.academy.transformation.LinearTransformation;
import backend.academy.transformation.NonlinearTransformation;
import backend.academy.transformation.Variation;
import java.io.StringWriter;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransformationStatisticsTest {
    @Test
    @DisplayName("Correctness test")
    void correctness(@Mock LinearTransformation lt1, @Mock LinearTransformation lt2) {
        when(lt1.red()).thenReturn(100);
        when(lt1.blue()).thenReturn(44);
        when(lt1.green()).thenReturn(343);
        when(lt1.probability()).thenReturn(0.1);
        when(lt1.coefficients()).thenReturn(List.of(0.1, 0.2, 0.3, 0.4, 0.5, 0.6));
        when(lt1.variations()).thenReturn(List.of(
            new Variation(0.3, NonlinearTransformation.SINUSOIDAL),
            new Variation(0.7, NonlinearTransformation.SPHERICAL)));

        when(lt2.red()).thenReturn(4);
        when(lt2.blue()).thenReturn(43);
        when(lt2.green()).thenReturn(23);
        when(lt2.probability()).thenReturn(0.9);
        when(lt2.coefficients()).thenReturn(List.of(0.4, 0.2, 0.3, 0.6, 0.5, 0.6));
        when(lt2.variations()).thenReturn(List.of(
            new Variation(0.3, NonlinearTransformation.DIAMOND),
            new Variation(0.7, NonlinearTransformation.SPHERICAL)));

        Statistics st = new TransformationStatistics(List.of(lt1, lt2));
        StringWriter sw = new StringWriter();
        new AdocReportWriter(sw).write(List.of(st));

        assertThat(sw.toString()).isEqualTo("""
            ==== Functions used for transformation
            |===
            | Field | Value
            | Probability | 0.100
            | Color | Red: 100, Green: 343, Blue: 44
            | Linear coefficients | [0.100, 0.200, 0.300, 0.400, 0.500, 0.600]
            | Variations | [Weight: 0.300, Func: SINUSOIDAL, Weight: 0.700, Func: SPHERICAL]
            |  |\s
            | Probability | 0.900
            | Color | Red: 4, Green: 23, Blue: 43
            | Linear coefficients | [0.400, 0.200, 0.300, 0.600, 0.500, 0.600]
            | Variations | [Weight: 0.300, Func: DIAMOND, Weight: 0.700, Func: SPHERICAL]
            |===
            """);
    }
}
