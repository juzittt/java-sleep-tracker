package ru.yandex.practicum.sleeptracker.function;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.SleepQuality;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SleeplessNightsAmountTest {

    private final SleeplessNightsAmount function = new SleeplessNightsAmount();

    @Test
    void shouldReturnZeroForEmptyList() {
        assertEquals("0", function.apply(List.of()));
    }

    @Test
    void shouldCountSleeplessNights() {
        var sessions = List.of(
                new SleepingSession(ldt(1, 23, 30), ldt(2, 7, 0), SleepQuality.GOOD),
                new SleepingSession(ldt(3, 22, 0), ldt(3, 23, 30), SleepQuality.BAD)
        );
        assertEquals("1", function.apply(sessions));
    }

    private LocalDateTime ldt(int day, int hour, int minute) {
        return LocalDateTime.of(2025, 4, day, hour, minute);
    }
}