package core.basesyntax;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class FileEditor {
    private static final String CREATE_DESCRIPTION
            = "'create [path] [file-name]' - создает или перезаписывает файл по указанному пути";
    private static final String READ_DESCRIPTION
            = "'read [path] [file-name]' - считывает и выводит в консоль файл по указанному пути";
    private static final String INFO_DESCRIPTION
            = "'info [path] [file-name]' - выводит в консоль информацию по указанному файлу";
    private static final String HELP_DESCRIPTION
            = "'help [command]' - выводит в консоль информацию по указанной команде";
    private static final String EXIT_DESCRIPTION
            = "'exit' - завершает работу";

    public void start() {
        System.out.println("Введите команду. Для просмотра всех доступных команд введите 'help'.");
        String input;
        do {
            input = new Scanner(System.in).nextLine();
            try {
                decide(input);
            } catch (IOException e) {
                throw new RuntimeException("Произошла ошибка!", e);
            }
        } while (!input.equals("exit"));
    }

    private void decide(String input) throws IOException {
        String[] tokens = input.split("\\s");
        if (assertLength(tokens, 3)) {
            switch (tokens[0]) {
                case "create":
                    create(Paths.get(tokens[1] + tokens[2]));
                    break;
                case "read":
                    read(Paths.get(tokens[1] + tokens[2]));
                    break;
                case "info":
                    info(Paths.get(tokens[1] + tokens[2]));
                    break;
                default:
                    write(input);
            }
        } else if (assertLength(tokens, 2) && tokens[0].equals("help")) {
            help(tokens[1]);
        } else if (assertLength(tokens, 1)) {
            switch (tokens[0]) {
                case "help":
                    help();
                    break;
                case "exit":
                    System.exit(0);
                    break;
                default:
                    write(input);
            }
        } else {
            write(input);
        }
    }

    private void create(Path file) throws IOException {
        if (Files.exists(file)) {
            System.out.println("Файл уже существует. Перезаписать? "
                    + "Введите 'да', чтобы продолжить, или введите другую команду:");
            String input = new Scanner(System.in).nextLine();
            if (input.equals("да")) {
                Files.delete(file);
            } else {
                decide(input);
                return;
            }
        }
        Files.createFile(file);
        System.out.println("Готово! Введите следующую команду:");
    }

    private void read(Path file) throws IOException {
        for (String line : Files.readAllLines(file)) {
            System.out.println(line);
        }
    }

    private void write(String input) throws IOException {
        String text = input;
        System.out.println("Вы хотите сохранить данный текст в файл? "
                + "Пожалуйста, введите команду 'save [path] [file-name]', "
                + "и текст будет записан в указанный файл");
        input = new Scanner(System.in).nextLine();
        String[] answer = input.split("\\s");
        if (answer[0].equals("save") && answer.length == 3) {
            Files.writeString(Paths.get(answer[1] + answer[2]), text);
            System.out.println("Текст записан! Введите следующую команду:");
        } else {
            System.out.println("Введите команду:");
        }
    }

    private boolean assertLength(String[] tokens, int length) {
        return tokens.length == length;
    }

    private void info(Path file) throws IOException {
        LocalDateTime dateTime = LocalDateTime.ofInstant(
                Files.getLastModifiedTime(file).toInstant(), ZoneId.systemDefault());
        String dateModified = dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE);
        String timeModified = dateTime.format(DateTimeFormatter.ISO_LOCAL_TIME).substring(0, 8);

        System.out.printf("Количество строк в файле - %d\n",
                Files.readAllLines(file).size());
        System.out.printf("Количество слов - %d\n",
                String.join(" ", Files.readAllLines(file)).split("\\s+").length);
        System.out.printf("Количество символов - %d\n",
                String.join("", Files.readAllLines(file)).length());
        System.out.printf("Дата и время последнего изменения - %s %s\n",
                dateModified, timeModified);
        System.out.printf("Размер файла - %d байт\n",
                Files.size(file));
    }

    private void help() {
        System.out.println("Доступные команды:");
        System.out.println(CREATE_DESCRIPTION);
        System.out.println(READ_DESCRIPTION);
        System.out.println(INFO_DESCRIPTION);
        System.out.println(HELP_DESCRIPTION);
        System.out.println(EXIT_DESCRIPTION);
    }

    private void help(String methodName) {
        switch (methodName) {
            case "create":
                System.out.println(CREATE_DESCRIPTION);
                break;
            case "read":
                System.out.println(READ_DESCRIPTION);
                break;
            case "info":
                System.out.println(INFO_DESCRIPTION);
                break;
            case "HELP":
                System.out.println(HELP_DESCRIPTION);
                break;
            case "exit":
                System.out.println(EXIT_DESCRIPTION);
                break;
            default:
                System.out.println("Такой программы не существует");
        }
    }

}
