package backend.academy.report.statistics;

import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TimeSpentImageProcessingStatistics implements Statistics {
    public static final long MILLISECONDS_PER_SECOND = TimeUnit.SECONDS.toMillis(1);
    private final long time;

    @Override
    public String getName() {
        return "Time spent on image conversion";
    }

    @Override
    public List<String> getTable() {
        return List.of("Time", String.format("%.3f seconds", (double) time / MILLISECONDS_PER_SECOND));
    }
}
