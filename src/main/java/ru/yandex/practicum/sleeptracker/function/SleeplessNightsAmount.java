package ru.yandex.practicum.sleeptracker.function;

import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Function;

public class SleeplessNightsAmount implements Function<List<SleepingSession>, String> {

    @Override
    public String apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return String.valueOf(0);
        }

        long nightsAmount = ChronoUnit.DAYS.between(
                sessions.getFirst().startTime().toLocalDate(),
                sessions.getLast().endTime().toLocalDate()
        );

        long sleepingNights = sessions.stream()
                .filter(s -> {
                    LocalTime start = s.startTime().toLocalTime();
                    int startDay = s.startTime().toLocalDate().getDayOfMonth();
                    int endDay = s.endTime().toLocalDate().getDayOfMonth();
                    boolean notSameDay = startDay != endDay;
                    return notSameDay || start.isBefore(LocalTime.of(6, 0));
                })
                .count();
        return String.valueOf(nightsAmount - sleepingNights);
    }
}