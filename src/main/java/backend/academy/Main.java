package backend.academy;

import backend.academy.argparser.Args;
import backend.academy.configparser.ConfigParser;
import backend.academy.display.FractalImageDisplay;
import backend.academy.display.FractalImageWriter;
import backend.academy.render.FractalImage;
import backend.academy.render.FractalRenderer;
import backend.academy.render.Pixel;
import backend.academy.shapes.Rect;
import backend.academy.transformation.LinearTransformation;
import com.beust.jcommander.JCommander;
import java.awt.image.BufferedImage;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        try {
            Args parsedArgs = new Args();
            JCommander parser = getParser(parsedArgs);
            parser.parse(args);
            if (parsedArgs.help()) {
                parser.usage();
                return;
            }

            FractalImage fractalImage = getFractalImage(parsedArgs);

            List<LinearTransformation> transformations = ConfigParser.parse(parsedArgs.config().toString());
            fractalImage = FractalRenderer.render(
                fractalImage,
                Rect.getMirror((double) fractalImage.width() / fractalImage.height() / parsedArgs.zoom(),
                    (double) 1 / parsedArgs.zoom()),
                transformations,
                10,
                parsedArgs.iterations(),
                parsedArgs.rotations());

            BufferedImage image = FractalImageWriter.write(fractalImage, parsedArgs.format(), parsedArgs.output());
            if (parsedArgs.show()) {
                FractalImageDisplay.display(image);
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private static FractalImage getFractalImage(Args parsedArgs) {
        Pixel[] pixels = new Pixel[parsedArgs.width() * parsedArgs.height()];
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = new Pixel(0, 0, 0, 0);
        }
        return new FractalImage(pixels, parsedArgs.width(), parsedArgs.height());
    }

    private static JCommander getParser(Args parsedArgs) {
        return JCommander.newBuilder()
            .addObject(parsedArgs)
            .build();
    }
}
