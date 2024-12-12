package backend.academy;

import backend.academy.argparser.Args;
import backend.academy.configparser.ConfigParser;
import backend.academy.display.FractalImageDisplay;
import backend.academy.display.FractalImageWriter;
import backend.academy.render.FractalImage;
import backend.academy.render.ImageUtils;
import backend.academy.render.shapes.Rect;
import backend.academy.report.ReportWriter;
import backend.academy.report.statistics.ArgumentParametersStatistics;
import backend.academy.report.statistics.Statistics;
import backend.academy.report.statistics.TimeSpentImageProcessingStatistics;
import backend.academy.report.statistics.TransformationStatistics;
import backend.academy.transformation.LinearTransformation;
import backend.academy.transformation.TransformationUtils;
import com.beust.jcommander.JCommander;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

@UtilityClass
@Log4j2
public class Main {
    private static final int SAMPLES = 20;
    private static final int TRANSFORMATIONS_COUNT = 20;
    private static final int AVERAGING_BOX_SIZE = 3;

    public static void main(String[] args) {
        try {
            Args parsedArgs = new Args();
            JCommander parser = getParser(parsedArgs);
            parser.parse(args);
            if (parsedArgs.help()) {
                parser.usage();
                return;
            }

            List<LinearTransformation> transformations = getTransformations(parsedArgs);

            long time = System.currentTimeMillis();
            FractalImage fractalImage = FractalImage.create(
                parsedArgs.width() * AVERAGING_BOX_SIZE,
                parsedArgs.height() * AVERAGING_BOX_SIZE);
            render(fractalImage, transformations, parsedArgs);
            fractalImage = ImageUtils.pixelAveraging(fractalImage, AVERAGING_BOX_SIZE);
            ImageUtils.gammaCorrection(fractalImage, parsedArgs.gamma());
            long elapsedTime = System.currentTimeMillis() - time;

            BufferedImage image = FractalImageWriter.write(fractalImage, parsedArgs.format(), parsedArgs.output());
            if (parsedArgs.show()) {
                FractalImageDisplay.display(image);
            }

            Statistics argsParams = new ArgumentParametersStatistics(parsedArgs);
            Statistics transformationStatistics = new TransformationStatistics(transformations);
            Statistics spentTime = new TimeSpentImageProcessingStatistics(elapsedTime);
            writeReport(parsedArgs, List.of(argsParams, transformationStatistics, spentTime));
        } catch (Exception e) {
            log.error("An error occurred: {}", e.getMessage());
        }
    }

    private static void writeReport(Args parsedArgs, List<Statistics> statistics) throws IOException {
        ReportWriter writer = parsedArgs.reportFormat().getReportWriter(
            Files.newBufferedWriter(parsedArgs.reportOutput(), StandardCharsets.US_ASCII));
        writer.write(statistics);
    }

    private static void render(FractalImage fractalImage, List<LinearTransformation> transformations, Args parsedArgs) {
        parsedArgs.threadType().getRenderer(
            transformations,
            SAMPLES,
            parsedArgs.iterations(),
            parsedArgs.rotations(),
            parsedArgs.symmetricX(),
            parsedArgs.symmetricY()
        ).render(
            fractalImage,
            Rect.getMirror((double) fractalImage.width() / fractalImage.height() / parsedArgs.zoom(),
                (double) 1 / parsedArgs.zoom())
        );
    }

    private static List<LinearTransformation> getTransformations(Args parsedArgs) throws IOException {
        List<LinearTransformation> transformations;
        if (parsedArgs.config() == null) {
            transformations =
                TransformationUtils.generateTransformations(parsedArgs.transformations(), TRANSFORMATIONS_COUNT);
        } else {
            transformations = ConfigParser.parse(parsedArgs.config());
        }
        return transformations;
    }

    private static JCommander getParser(Args parsedArgs) {
        return JCommander.newBuilder()
            .addObject(parsedArgs)
            .build();
    }
}
