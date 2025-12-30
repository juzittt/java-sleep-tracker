package ru.yandex.practicum.sleeptracker.function;

import ru.yandex.practicum.sleeptracker.SleepQuality;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.List;
import java.util.function.Function;


public class BadSessionsAmount implements Function<List<SleepingSession>, String> {
    @Override
    public String apply(List<SleepingSession> sessions) {
        long result = sessions.stream()
                .filter(s -> s.sleepQuality().equals(SleepQuality.BAD))
                .count();
        return String.valueOf(result);
    }
}