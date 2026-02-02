package ru.yandex.practicum.sleeptracker.function;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.SleepQuality;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("SessionsAmount — подсчёт общего количества сессий")
class SessionsAmountTest {

    private static final LocalDateTime TIME = LocalDateTime.of(2025, 4, 5, 10, 0);
    private static final String TWO = "2";

    private final SessionsAmount function = new SessionsAmount();

    @Test
    @DisplayName("Пустой список → возвращает '0'")
    void apply_emptyList_returnsZero() {
        List<SleepingSession> sessions = List.of();

        String result = function.apply(sessions);

        assertEquals("0", result);
    }

    @Test
    @DisplayName("Две сессии → возвращает '2'")
    void apply_twoSessions_returnsTwo() {
        var sessions = List.of(
                new SleepingSession(TIME, TIME.plusHours(1), SleepQuality.GOOD),
                new SleepingSession(TIME.plusHours(2), TIME.plusHours(3), SleepQuality.BAD)
        );

        String result = function.apply(sessions);

        assertEquals(TWO, result);
    }
}