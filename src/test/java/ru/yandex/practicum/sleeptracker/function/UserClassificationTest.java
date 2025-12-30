package ru.yandex.practicum.sleeptracker.function;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.SleepQuality;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserClassificationTest {

    private final UserClassification function = new UserClassification();

    @Test
    void shouldReturnDefaultIfEmpty() {
        assertEquals("список сонных сессий пуст", function.apply(List.of()));
    }

    @Test
    void shouldBeSova() {
        var sessions = List.of(
                new SleepingSession(ldt(1, 23, 30), ldt(2, 10, 0), SleepQuality.GOOD),
                new SleepingSession(ldt(2, 23, 45), ldt(3, 9, 30), SleepQuality.GOOD)
        );
        assertEquals("Сова", function.apply(sessions));
    }


    @Test
    void shouldBeGolub() {
        var sessions = List.of(
                new SleepingSession(ldt(1, 22, 30), ldt(2, 6, 0), SleepQuality.GOOD),
                new SleepingSession(ldt(2, 23, 15), ldt(3, 7, 30), SleepQuality.GOOD)
        );
        assertEquals("Голубь", function.apply(sessions));
    }

    @Test
    void shouldDefaultToGolubIfTieOrUndefined() {
        var sessions = List.of(
                new SleepingSession(ldt(1, 12, 0), ldt(1, 14, 0), SleepQuality.GOOD)
        );
        assertEquals("Голубь", function.apply(sessions));
    }

    private LocalDateTime ldt(int day, int hour, int minute) {
        return LocalDateTime.of(2025, 4, day, hour, minute);
    }
}