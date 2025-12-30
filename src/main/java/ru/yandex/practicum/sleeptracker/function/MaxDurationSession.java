package ru.yandex.practicum.sleeptracker.function;

import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class MaxDurationSession implements Function<List<SleepingSession>, String> {
    @Override
    public String apply(List<SleepingSession> sessions) {
        long result = sessions.stream()
                .map(s -> Duration.between(s.startTime(), s.endTime()))
                .map(Duration::toMinutes)
                .max(Long::compareTo)
                .orElse(0L);
        return String.valueOf(result);
    }
}