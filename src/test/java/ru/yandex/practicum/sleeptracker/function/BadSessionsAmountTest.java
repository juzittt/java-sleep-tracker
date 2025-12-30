package ru.yandex.practicum.sleeptracker.function;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.SleepQuality;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BadSessionsAmountTest {

    private final BadSessionsAmount function = new BadSessionsAmount();

    @Test
    void shouldReturnZeroIfNoBadSessions() {
        var sessions = List.of(
                new SleepingSession(dt("10:00"), dt("12:00"), SleepQuality.GOOD),
                new SleepingSession(dt("14:00"), dt("15:00"), SleepQuality.NORMAL)
        );
        assertEquals("0", function.apply(sessions));
    }

    @Test
    void shouldCountBadSessions() {
        var sessions = List.of(
                new SleepingSession(dt("10:00"), dt("12:00"), SleepQuality.BAD),
                new SleepingSession(dt("13:00"), dt("14:00"), SleepQuality.BAD),
                new SleepingSession(dt("15:00"), dt("16:00"), SleepQuality.GOOD)
        );
        assertEquals("2", function.apply(sessions));
    }

    private LocalDateTime dt(String time) {
        return LocalDateTime.parse("2025-04-05T" + time + ":00");
    }
}