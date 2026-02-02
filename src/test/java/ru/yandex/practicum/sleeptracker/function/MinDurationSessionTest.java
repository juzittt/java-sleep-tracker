package ru.yandex.practicum.sleeptracker.function;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.SleepQuality;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("MinDurationSession — подсчёт минимальной продолжительности сессии")
class MinDurationSessionTest {

    private static final LocalDateTime BASE_TIME = LocalDateTime.of(2025, 4, 5, 0, 0);

    private final MinDurationSession function = new MinDurationSession();

    @Test
    @DisplayName("Пустой список → возвращает '0'")
    void apply_emptyList_returnsZero() {
        List<SleepingSession> sessions = List.of();

        String result = function.apply(sessions);

        assertEquals("0", result);
    }

    @Test
    @DisplayName("Сессии 120, 30, 15 минут → возвращает '15'")
    void apply_sessionsWithDifferentDurations_returnsMin() {
        var sessions = List.of(
                createSessionWithDuration(BASE_TIME, 120),
                createSessionWithDuration(BASE_TIME.plusHours(2), 30),
                createSessionWithDuration(BASE_TIME.plusHours(4), 15)
        );

        String result = function.apply(sessions);

        assertEquals("15", result);
    }

    private SleepingSession createSessionWithDuration(LocalDateTime start, int durationMinutes) {
        return new SleepingSession(start, start.plusMinutes(durationMinutes), SleepQuality.GOOD);
    }
}