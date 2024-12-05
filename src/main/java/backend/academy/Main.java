package backend.academy;

import backend.academy.argparser.Args;
import com.beust.jcommander.JCommander;
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

            System.out.println(parsedArgs);
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private static JCommander getParser(Args parsedArgs) {
        return JCommander.newBuilder()
            .addObject(parsedArgs)
            .build();
    }
}
