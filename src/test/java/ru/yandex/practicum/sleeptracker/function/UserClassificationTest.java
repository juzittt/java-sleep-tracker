package ru.yandex.practicum.sleeptracker.function;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.SleepQuality;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("UserClassification — классификация пользователя по типу сна")
class UserClassificationTest {

    private static final String SOVA = "Сова";
    private static final String DOVE = "Голубь";
    private static final String EMPTY = "Список сонных сессий пуст";

    private final UserClassification function = new UserClassification();

    @Test
    @DisplayName("Пустой список → возвращает сообщение об отсутствии сессий")
    void apply_emptyList_returnsEmptyMessage() {
        List<SleepingSession> sessions = List.of();

        String result = function.apply(sessions);

        assertEquals(EMPTY, result);
    }

    @Test
    @DisplayName("Сессии подходят под 'Сова' → возвращает 'Сова'")
    void apply_sessionsMatchSova_returnsSova() {
        var sessions = List.of(
                session(ldt(1, 23, 30), ldt(2, 10, 0)),
                session(ldt(2, 23, 45), ldt(3, 9, 30))
        );

        String result = function.apply(sessions);

        assertEquals(SOVA, result);
    }

    @Test
    @DisplayName("Сессии подходят под 'Голубь' → возвращает 'Голубь'")
    void apply_sessionsMatchGolub_returnsGolub() {
        var sessions = List.of(
                session(ldt(1, 22, 30), ldt(2, 6, 0)),
                session(ldt(2, 23, 15), ldt(3, 7, 30))
        );

        String result = function.apply(sessions);

        assertEquals(DOVE, result);
    }

    @Test
    @DisplayName("Равное количество типов → возвращает 'Голубь'")
    void apply_tieBetweenTypes_returnsGolub() {
        var sessions = List.of(
                session(ldt(1, 23, 30), ldt(2, 10, 0)), // Совá
                session(ldt(1, 5, 30), ldt(2, 6, 0))     // Жаворонок: 5:30 → 6:00, notSameDay = true
        );

        String result = function.apply(sessions);

        assertEquals("Голубь", result);
    }

    private LocalDateTime ldt(int day, int hour, int minute) {
        return LocalDateTime.of(2025, 4, day, hour, minute);
    }

    private SleepingSession session(LocalDateTime start, LocalDateTime end) {
        return new SleepingSession(start, end, SleepQuality.GOOD);
    }
}