package backend.academy;

import backend.academy.argparser.Args;
import backend.academy.configparser.ConfigParser;
import backend.academy.display.FractalImageDisplay;
import backend.academy.display.FractalImageWriter;
import backend.academy.render.FractalImage;
import backend.academy.render.renderer.ExecutorServiceFractalRenderer;
import backend.academy.render.shapes.Rect;
import backend.academy.transformation.LinearTransformation;
import com.beust.jcommander.JCommander;
import java.awt.image.BufferedImage;
import java.util.List;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

@UtilityClass
@Log4j2
public class Main {

    public static final int SAMPLES = 20;

    public static void main(String[] args) {
        try {
            Args parsedArgs = new Args();
            JCommander parser = getParser(parsedArgs);
            parser.parse(args);
            if (parsedArgs.help()) {
                parser.usage();
                return;
            }

            FractalImage fractalImage = FractalImage.create(parsedArgs.width(), parsedArgs.height());

            List<LinearTransformation> transformations = ConfigParser.parse(parsedArgs.config());
            new ExecutorServiceFractalRenderer().render(
                fractalImage,
                Rect.getMirror((double) fractalImage.width() / fractalImage.height() / parsedArgs.zoom(),
                    (double) 1 / parsedArgs.zoom()),
                transformations,
                SAMPLES,
                parsedArgs.iterations(),
                parsedArgs.rotations());

            BufferedImage image = FractalImageWriter.write(fractalImage, parsedArgs.format(), parsedArgs.output());
            if (parsedArgs.show()) {
                FractalImageDisplay.display(image);
            }
        } catch (Exception e) {
            log.error("An error occurred: {}", e.getMessage());
        }
    }

    private static JCommander getParser(Args parsedArgs) {
        return JCommander.newBuilder()
            .addObject(parsedArgs)
            .build();
    }
}
