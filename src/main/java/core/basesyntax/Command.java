package core.basesyntax;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Command {
    private static final String SEPARATOR = File.separator;
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yy HH:mm:ss");
    private Scanner scanner = new Scanner(System.in);

    public void create(String path, String name) throws IOException {
        if (Files.exists(Paths.get(path))) {
            Path filePath = Paths.get(path + SEPARATOR + name);
            if (Files.exists(filePath)) {
                System.out.println("Файлы существует, перезаписать его? Да или нет?");
                String answerRead = scanner.next();
                if (answerRead.equalsIgnoreCase("да")) {
                    System.out.println("Что перезаписать в файл?\n"
                            + "Что бы остановить вводить текст напишите: стоп");
                    String text = scanner.nextLine();
                    StringBuilder stringBuilder = new StringBuilder();
                    while (!text.equals("стоп")) {
                        stringBuilder.append(text).append("\n");
                        text = scanner.nextLine();
                    }
                    writeToFile(stringBuilder.toString(), filePath);
                    System.out.println("Файл успешно перезаписан.");
                }
            } else {
                System.out.println("Записать что-то в файл?\n"
                        + "Что бы остановить вводить текст напишите: стоп");
                String text = scanner.nextLine();
                StringBuilder stringBuilder = new StringBuilder();
                while (!text.equals("стоп")) {
                    stringBuilder.append(text).append("\n");
                    text = scanner.nextLine();
                }
                Files.createFile(filePath);
                System.out.println("Файл " + name + " создан");
            }
        } else {
            System.out.println("Указанной папки не существует.");
        }
    }

    public void read(String path, String name) throws IOException {
        Path filePath = Paths.get(path + SEPARATOR + name);
        if (Files.exists(filePath)) {
            List<String> lines = Files.readAllLines(filePath);
            for (String text : lines) {
                System.out.println(text);
            }
        } else {
            System.out.println("Файл не найден.");
        }
    }

    public void info(String path, String name) throws IOException {
        Path filePath = Paths.get(path + SEPARATOR + name);
        if (Files.exists(filePath)) {
            LocalDateTime date = Instant
                    .ofEpochMilli(Files.getLastModifiedTime(filePath).toMillis())
                    .atZone(ZoneId.systemDefault()).toLocalDateTime();
            String text = new String(Files.readAllBytes(filePath));
            String result = "Информация по найденному файлу\n"
                    + "Количество символов - " + text.replaceAll("\n", "").length() + "\n"
                    + "Количество строк - " + Files.lines(filePath).count() + "\n"
                    + "Количество слов - " + text.replaceAll("\n", " ").trim().split(" ")
                    .length + "\n"
                    + "Дата последних изменений - " + date.format(FORMATTER) + "\n"
                    + "Размер файла - " + Files.size(filePath) + " байт";
            System.out.println(result);
        } else {
            System.out.println("Файл не найден.");
        }
    }

    private static void writeToFile(String text, Path filePath) throws IOException {
        Files.write(filePath, text.getBytes());
    }

    public void help() {
        String[] commands = {"create", "read", "info", "exit"};
        System.out.println("Пример ввода пути папки: C:\\Document\n"
                + "Пример ввода файла: text.txt\n"
                + "Пример ввода команды: read C:\\Document text.txt"
                + "Все доступные команды: ");
        for (String command : commands) {
            help(command);
        }
    }

    public void help(String command) {

        String answer = command + " : ";
        switch (command.toLowerCase()) {
            case "create":
                answer += "Создает файл с указанием пути.\n";
                break;
            case "read":
                answer += "Читает файл и выводит его содержимое в консоль.\n";
                break;
            case "info":
                answer += "Выводит краткую информацию по указанному "
                        + "файлу: количество символов, \n"
                        + "строк, слов, дату и время последнего изменения, размер файла.\n";
                break;
            case "exit":
                answer += "Завершает работу программы.\n";
                break;
            default:
                answer += "Такой команды не существует\n";
                break;
        }
        System.out.println(answer);
    }

    public void save(String text) throws IOException {
        System.out.println("Сохраняю текст\n"
                + "Введите полный путь к файлу с расширение .txt\n"
                + "Пример ввода пути к файлу: C:\\Documents\\example.txt\n"
                + "Если ошиблись с вводом, перезапустите программу и введите help");
        String path = scanner.nextLine();
        if (Files.exists(Paths.get(path))) {
            System.out.println("Файлы существует, перезаписать его? Да или нет?");
            String answerRead = scanner.next();
            if (answerRead.equalsIgnoreCase("да")) {
                writeToFile(text, Paths.get(path));
            } else {
                System.out.println("Введите новый путь к файлу.");
                save(text);
            }
        } else {
            Files.createFile(Paths.get(path));
            writeToFile(text, Paths.get(path));
        }
    }

    public void exit() {
        System.out.println("Программа закончила своЮ работу.");
        scanner.close();
        System.exit(0);
    }
}
