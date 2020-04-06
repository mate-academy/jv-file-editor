package core.basesyntax;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Feel free to remove this class and create your own.
 */
public class FileEditor {
    private static final String WARNING = "Неверно введена команда.";
    private static final String GREETING =
            "Введите команду. Для просмотра всех команд введите [help].";
    private static final String[] DESCRIPTION = new String[] {
            "create [path] [file-name] - Создает текстовый файл по указанному пути.",
            "read [path] [file-name] - Считывает файл по указанному пути и выводит текст в консоль",
            "info [path] [file-name] - Выводит краткую информацию по указанному файлу.",
            "help - Выводит в консоль все доступные комманды и информацию к ним.",
            "help [command] - Выводит в консоль информацию по указанной команде.",
            "exit - Завершение работы программы."};

    public static void create(String path, String fileName) throws IOException {
        Scanner scanner = new Scanner(System.in);
        if (Files.notExists(Paths.get(path))) {
            System.out.println("Введённый путь не существует.");
            return;
        }
        if (Files.exists(Paths.get(path + fileName))) {
            System.out.println("Файл уже существует. Перезаписать? Y/N");
            String answer = scanner.nextLine();
            if (answer.equals("Y")) {
                Files.write(Paths.get(path + fileName), "".getBytes());
                System.out.println("Файл перезаписан.");
            }
            return;
        }
        Files.createFile(Paths.get(path + fileName));
        System.out.println("Файл создан.");
    }

    public static void read(String path, String fileName) throws IOException {
        if (Files.notExists(Paths.get(path))) {
            System.out.println("Введённый путь не существует.");
            return;
        }
        if (Files.notExists(Paths.get(path + fileName))) {
            System.out.println("Файл не существует.");
            return;
        }
        String content = Files.readString(Paths.get(path + fileName));
        System.out.println(content);
    }

    public static void info(String path, String fileName) throws IOException {
        Path pathFul = Paths.get(path + fileName);
        FileTime time = Files.getLastModifiedTime(pathFul);
        ZonedDateTime modTime = ZonedDateTime.ofInstant(time.toInstant(),
                ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy HH:mm:dd z");
        System.out.println("Время последней модификации файла - " + modTime.format(formatter));
        System.out.println("Количество строк - " + Files.readAllLines(pathFul).size());
        System.out.println("Количестов слов - " + Files.readString(pathFul).split(" ").length);
        System.out.println("Размер файла - " + Files.size(pathFul));
        System.out.println("Количество симолов - " + Files.readString(pathFul).split("").length);
    }

    public static void help(String[] enter) {
        if (enter.length == 2) {
            switch (enter[1]) {
                case "create" : {
                    System.out.println(DESCRIPTION[0]);
                    break;
                }
                case "read" : {
                    System.out.println(DESCRIPTION[1]);
                    break;
                }
                case "info" : {
                    System.out.println(DESCRIPTION[2]);
                    break;
                }
                case "exit" : {
                    System.out.println(DESCRIPTION[5]);
                    break;
                }
                default: {
                    System.out.println(WARNING);
                    break;
                }
            }
        } else {
            for (String descr : DESCRIPTION) {
                System.out.println(descr);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println(GREETING);
        String enteredString = scanner.nextLine();
        while (!enteredString.equals("exit")) {
            String[] enStringAsArray = enteredString.split(" ");
            switch (enStringAsArray[0]) {
                case "create" : {
                    if (enStringAsArray.length == 3) {
                        create(enStringAsArray[1], enStringAsArray[2]);
                    } else {
                        System.out.println(WARNING);
                    }
                    break;
                }
                case "read" : {
                    if (enStringAsArray.length == 3) {
                        read(enStringAsArray[1], enStringAsArray[2]);
                    } else {
                        System.out.println(WARNING);
                    }
                    break;
                }
                case "info" : {
                    if (enStringAsArray.length == 3) {
                        info(enStringAsArray[1], enStringAsArray[2]);
                    } else {
                        System.out.println(WARNING);
                    }
                    break;
                }
                case "help" : {
                    help(enStringAsArray);
                    break;
                }
                default : {
                    System.out.println("Сохранить текст? Y/N");//тут можно реализовать сохранение
                    scanner.nextLine(); //, но в ТЗ сказано "...должно появиться сообщение"
                    break;
                }
            }
            System.out.println(GREETING);
            enteredString = scanner.nextLine();
        }
    }

}
