package ru.yandex.practicum.sleeptracker.function;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.SleepQuality;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("AverageDurationSession — подсчёт средней продолжительности сессии")
class AverageDurationSessionTest {

    private static final LocalDateTime BASE_DATE = LocalDateTime.of(2025, 4, 5, 0, 0);
    private static final int SESSION_120_MIN = 120;
    private static final int SESSION_60_MIN = 60;
    private static final String EXPECTED_AVERAGE = "70";

    private final AverageDurationSession function = new AverageDurationSession();

    @Test
    @DisplayName("Пустой список → возвращает '0'")
    void apply_emptyList_returnsZero() {
        List<SleepingSession> sessions = List.of();

        String result = function.apply(sessions);

        assertEquals("0", result);
    }

    @Test
    @DisplayName("Сессии 120, 60, 30 минут → среднее 70 минут")
    void apply_sessionsWithDifferentDurations_returnsRoundedAverage() {
        var sessions = List.of(
                sessionWithDuration(0, 2, SESSION_120_MIN),
                sessionWithDuration(2, 3, SESSION_60_MIN),
                sessionWithDuration(4, 4, 30)
        );

        String result = function.apply(sessions);

        assertEquals(EXPECTED_AVERAGE, result);
    }

    private SleepingSession sessionWithDuration(int startHour, int endHour, int durationMinutes) {
        return new SleepingSession(
                BASE_DATE.plusHours(startHour),
                BASE_DATE.plusHours(endHour).plusMinutes(durationMinutes % 60),
                SleepQuality.GOOD
        );
    }
}