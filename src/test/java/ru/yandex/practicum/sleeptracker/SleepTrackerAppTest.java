package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("SleepTrackerApp — парсинг строк лога сна")
class SleepTrackerAppTest {

    private static final String VALID_LINE = "01.12.24 23:00;02.12.24 07:00;GOOD";
    private static final String INVALID_LINE = "01.12.24 23:00;GOOD";
    private static final LocalDateTime EXPECTED_START = LocalDateTime.of(2024, 12, 1, 23, 0);
    private static final LocalDateTime EXPECTED_END = LocalDateTime.of(2024, 12, 2, 7, 0);

    @Test
    @DisplayName("Корректная строка → создаёт сессию с правильными полями")
    void createSleepingSession_validLine_createsCorrectSession() {
        SleepingSession session = SleepTrackerApp.createSleepingSession(VALID_LINE);

        assertEquals(EXPECTED_START, session.startTime());
        assertEquals(EXPECTED_END, session.endTime());
        assertEquals(SleepQuality.GOOD, session.sleepQuality());
    }

    @Test
    @DisplayName("Некорректная строка → выбрасывает RuntimeException")
    void createSleepingSession_invalidLine_throwsException() {
        assertThrows(RuntimeException.class, () -> {
            SleepTrackerApp.createSleepingSession(INVALID_LINE);
        });
    }
}