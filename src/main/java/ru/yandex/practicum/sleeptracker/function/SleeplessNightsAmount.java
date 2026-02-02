package ru.yandex.practicum.sleeptracker.function;

import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

public class SleeplessNightsAmount implements Function<List<SleepingSession>, String> {

    @Override
    public String apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return String.valueOf(0);
        }

        LocalDate firstDate = sessions.get(0).startTime().toLocalDate();
        LocalDate lastDate = sessions.get(sessions.size() - 1).endTime().toLocalDate();

        long sleeplessNights = 0;

        for (LocalDate date = firstDate; !date.isAfter(lastDate); date = date.plusDays(1)) {
            LocalDateTime nightStart = date.atStartOfDay();
            LocalDateTime nightEnd = nightStart.plusDays(1).withHour(6).withMinute(0);

            boolean covered = sessions.stream().anyMatch(session ->
                    isOverlapping(session, nightStart, nightEnd)
            );

            if (!covered) {
                sleeplessNights++;
            }
        }

        return String.valueOf(sleeplessNights);
    }

    private boolean isOverlapping(SleepingSession session, LocalDateTime nightStart, LocalDateTime nightEnd) {
        LocalDateTime sessionStart = session.startTime();
        LocalDateTime sessionEnd = session.endTime();

        return sessionStart.isBefore(nightEnd) && sessionEnd.isAfter(nightStart);
    }
}