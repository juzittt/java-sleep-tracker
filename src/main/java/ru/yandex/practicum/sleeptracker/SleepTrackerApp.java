package ru.yandex.practicum.sleeptracker;

import ru.yandex.practicum.sleeptracker.function.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
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
    private static final String DEFAULT_PATH = "src/main/resources/sleep_log.txt";
    private static final Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);


    public static void main(String[] args) {
        userChoice();
        processSleepingSessions();
        scanner.close();
    }

    private static void userChoice() {
        System.out.println("\nСтатистика сна\n");
        System.out.println("Выберите способ загрузки файла:");
        System.out.println("1. Использовать файл по умолчанию");
        System.out.println("2. Импорт из файла");

        int choice = readUserChoice();

        switch (choice) {
            case 1:
                path = Path.of(DEFAULT_PATH);
                System.out.println("Выбран путь по умолчанию: " + path.toAbsolutePath());
                break;
            case 2:
                path = readCustomPath();
                if (path == null) {
                    System.err.println("Ввод отменён или путь некорректен.");
                    return;
                }
                System.out.println("Используется введённый путь: " + path.toAbsolutePath());
                break;
            default:
                System.err.println("Неверный выбор. Завершение работы.");
                return;
        }

        if (!Files.exists(path)) {
            System.err.println("Файл не найден: " + path.toAbsolutePath());
            return;
        }

        if (!Files.isReadable(path)) {
            System.err.println("Файл существует, но недоступен для чтения: " + path.toAbsolutePath());
        }
    }

    private static int readUserChoice() {
        while (true) {
            System.out.print("Введите 1 или 2: ");
            try {
                if (scanner.hasNextInt()) {
                    int choice = scanner.nextInt();
                    scanner.nextLine();
                    if (choice == 1 || choice == 2) {
                        return choice;
                    } else {
                        System.out.println("Пожалуйста, введите 1 или 2.");
                    }
                } else {
                    System.out.println("Некорректный ввод. Ожидается число.");
                    scanner.nextLine();
                }
            } catch (Exception e) {
                System.out.println("Ошибка ввода: " + e.getMessage());
            }
        }
    }

    private static Path readCustomPath() {
        System.out.print("Введите полный путь к файлу с логом сна: ");
        String input = scanner.nextLine().trim();

        if (input.isEmpty()) {
            return null;
        }

        Path customPath = Path.of(input);

        if (!customPath.isAbsolute()) {
            customPath = new File(customPath.toString()).getAbsoluteFile().toPath();
        }

        return customPath;
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