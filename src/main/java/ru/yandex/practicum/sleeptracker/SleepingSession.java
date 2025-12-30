package ru.yandex.practicum.sleeptracker;

import java.time.LocalDateTime;


public record SleepingSession(
        LocalDateTime startTime, LocalDateTime endTime, SleepQuality sleepQuality) {
}