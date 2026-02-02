package ru.yandex.practicum.sleeptracker.function;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.SleepQuality;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("BadSessionsAmount — подсчёт сессий с плохим качеством сна")
class BadSessionsAmountTest {

    private static final LocalDateTime TIME = LocalDateTime.of(2025, 4, 5, 10, 0);
    private static final String ZERO = "0";
    private static final String TWO = "2";

    private final BadSessionsAmount function = new BadSessionsAmount();

    @Test
    @DisplayName("Нет сессий с качеством BAD → возвращает '0'")
    void apply_noBadSessions_returnsZero() {
        var sessions = List.of(
                new SleepingSession(TIME, TIME.plusHours(1), SleepQuality.GOOD),
                new SleepingSession(TIME.plusHours(2), TIME.plusHours(3), SleepQuality.NORMAL)
        );

        String result = function.apply(sessions);

        assertEquals(ZERO, result);
    }

    @Test
    @DisplayName("Две сессии с качеством BAD → возвращает '2'")
    void apply_twoBadSessions_returnsTwo() {
        var sessions = List.of(
                new SleepingSession(TIME, TIME.plusHours(1), SleepQuality.BAD),
                new SleepingSession(TIME.plusHours(3), TIME.plusHours(4), SleepQuality.BAD),
                new SleepingSession(TIME.plusHours(5), TIME.plusHours(6), SleepQuality.GOOD)
        );

        String result = function.apply(sessions);

        assertEquals(TWO, result);
    }
}