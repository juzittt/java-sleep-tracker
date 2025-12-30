package ru.yandex.practicum.sleeptracker.function;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.SleepQuality;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SessionsAmountTest {

    private final SessionsAmount function = new SessionsAmount();

    @Test
    void shouldReturnZeroForEmptyList() {
        assertEquals("0", function.apply(List.of()));
    }

    @Test
    void shouldReturnCountOfSessions() {
        var sessions = List.of(
                new SleepingSession(LocalDateTime.now(), LocalDateTime.now().plusHours(1), SleepQuality.GOOD),
                new SleepingSession(LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(3), SleepQuality.BAD)
        );
        assertEquals("2", function.apply(sessions));
    }
}