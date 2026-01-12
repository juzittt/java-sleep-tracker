package ru.yandex.practicum.sleeptracker.function;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.SleepQuality;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("SleeplessNightsAmount — подсчёт бессонных ночей")
class SleeplessNightsAmountTest {

    private final SleeplessNightsAmount function = new SleeplessNightsAmount();

    @Test
    @DisplayName("Пустой список → возвращает '0'")
    void apply_emptyList_returnsZero() {
        List<SleepingSession> sessions = List.of();

        String result = function.apply(sessions);

        assertEquals("0", result);
    }

    @Test
    @DisplayName("Сессия с 23:30 до 05:00 покрывает ночь 1→2 → бессонных ночей нет → возвращает '0'")
    void apply_singleOvernightSession_returnsZeroSleeplessNights() {
        var sessions = List.of(
                new SleepingSession(ldt(1, 23, 30), ldt(2, 5, 0), SleepQuality.GOOD)
        );
        assertEquals("0", function.apply(sessions));
    }

    private LocalDateTime ldt(int day, int hour, int minute) {
        return LocalDateTime.of(2025, 4, day, hour, minute);
    }
}