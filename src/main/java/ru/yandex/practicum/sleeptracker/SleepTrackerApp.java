package ru.yandex.practicum.sleeptracker;

import ru.yandex.practicum.sleeptracker.function.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class SleepTrackerApp {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
    private static final List<Function<List<SleepingSession>, String>> FUNCTIONS = List.of(
            new SessionsAmount(),
            new MinDurationSession(),
            new MaxDurationSession(),
            new AverageDurationSession(),
            new BadSessionsAmount(),
            new SleeplessNightsAmount(),
            new UserClassification()
    );
    private static List<SleepingSession> sleepingSessions;
    private static Path path;
    private static SleepAnalysisResult result;


    public static void main(String[] args) {
        path = Path.of("src/main/resources/sleep_log.txt");
        if (!Files.exists(path)) {
            System.err.println("Файл не найден: " + path);
            return;
        }

        processSleepingSessions();
    }

    private static void processSleepingSessions() {
        result = new SleepAnalysisResult();
        loadSleepingSessions();
        if (sleepingSessions.isEmpty()) {
            System.err.println("Файл пуст или не содержит валидных сессий.");
            return;
        }
        runAnalytics();
    }

    private static void loadSleepingSessions() {
        sleepingSessions = new ArrayList<>();
        try (var reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            reader.lines()
                    .map(SleepTrackerApp::createSleepingSession)
                    .forEach(sleepingSessions::add);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтении файла: " + path, e);
        }
    }

    static SleepingSession createSleepingSession(String line) {
        String[] elements = line.split(";");
        LocalDateTime sleepStartTime = LocalDateTime.parse(elements[0], DATE_TIME_FORMATTER);
        LocalDateTime sleepEndTime = LocalDateTime.parse(elements[1], DATE_TIME_FORMATTER);
        SleepQuality quality = SleepQuality.valueOf(elements[2]);
        return new SleepingSession(sleepStartTime, sleepEndTime, quality);
    }

    private static void runAnalytics() {
        if (FUNCTIONS.isEmpty()) {
            System.err.println("Ошибка: список функций пуст.");
            return;
        }
        FUNCTIONS.forEach(f -> {
            String value = f.apply(sleepingSessions);
            System.out.println(result.getMessage(value));
        });
    }
}