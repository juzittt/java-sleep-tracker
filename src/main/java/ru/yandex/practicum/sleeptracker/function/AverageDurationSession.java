package ru.yandex.practicum.sleeptracker.function;

import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AverageDurationSession implements Function<List<SleepingSession>, String> {
    @Override
    public String apply(List<SleepingSession> sessions) {
        int result = sessions.stream()
                .map(s -> Duration.between(s.startTime(), s.endTime()))
                .map(Duration::toMinutes)
                .collect(Collectors.averagingInt(Long::intValue))
                .intValue();
        return String.valueOf(result);
    }
}