package ru.yandex.practicum.sleeptracker.function;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.SleepQuality;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("MaxDurationSession — подсчёт максимальной продолжительности сессии")
class MaxDurationSessionTest {

    private static final LocalDateTime BASE_TIME = LocalDateTime.of(2025, 4, 5, 10, 0);
    private static final int DURATION_120_MIN = 120;
    private static final int DURATION_150_MIN = 150;
    private static final int DURATION_45_MIN = 45;

    private final MaxDurationSession function = new MaxDurationSession();

    @Test
    @DisplayName("Пустой список → возвращает '0'")
    void apply_emptyList_returnsZero() {
        List<SleepingSession> sessions = List.of();

        String result = function.apply(sessions);

        assertEquals("0", result);
    }

    @Test
    @DisplayName("Сессии 120, 150, 45 минут → возвращает '150'")
    void apply_sessionsWithDifferentDurations_returnsMax() {
        var sessions = List.of(
                createSessionWithDuration(BASE_TIME, DURATION_120_MIN),
                createSessionWithDuration(BASE_TIME.plusHours(3), DURATION_150_MIN),
                createSessionWithDuration(BASE_TIME.plusHours(8), DURATION_45_MIN)
        );

        String result = function.apply(sessions);

        assertEquals("150", result);
    }

    private SleepingSession createSessionWithDuration(LocalDateTime start, int durationMinutes) {
        LocalDateTime end = start.plusMinutes(durationMinutes);
        return new SleepingSession(start, end, SleepQuality.GOOD);
    }
}