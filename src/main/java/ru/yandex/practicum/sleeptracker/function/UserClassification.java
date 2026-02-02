package ru.yandex.practicum.sleeptracker.function;

import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UserClassification implements Function<List<SleepingSession>, String> {

    @Override
    public String apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return "Список сонных сессий пуст";
        }

        return sessions.stream()
                .collect(Collectors.groupingBy(s -> {
                    LocalTime start = s.startTime().toLocalTime();
                    LocalTime end = s.endTime().toLocalTime();
                    int startDay = s.startTime().toLocalDate().getDayOfMonth();
                    int endDay = s.endTime().toLocalDate().getDayOfMonth();
                    boolean notSameDay = startDay != endDay;
                    if ((start.isAfter(LocalTime.of(23, 0))
                            || start.isBefore(LocalTime.of(6, 0)))
                            && end.isAfter(LocalTime.of(9, 0))) {
                        return "Сова";
                    } else if (start.isBefore(LocalTime.of(22, 0))
                            && end.isBefore(LocalTime.of(7, 0))
                            && notSameDay) {
                        return "Жаворонок";
                    } else if (notSameDay) {
                        return "Голубь";
                    }
                    return "undefined";
                }, Collectors.counting()))
                .entrySet().stream()
                .filter(e -> !e.getKey().equals("undefined"))
                .collect(Collectors.groupingBy(Map.Entry::getValue, Collectors.toList()))
                .entrySet().stream()
                .max(Map.Entry.comparingByKey())
                .map(maxEntry -> {
                    List<Map.Entry<String, Long>> maxTypes = maxEntry.getValue();
                    return maxTypes.size() > 1 ? "Голубь" : maxTypes.get(0).getKey();
                })
                .orElse("Голубь");
    }
}