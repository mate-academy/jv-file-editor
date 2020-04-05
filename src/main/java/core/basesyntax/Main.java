package core.basesyntax;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        while (true) {
            System.out.println("Type command");
            showDialog();
        }
    }

    private static void showDialog() {
        System.out.println("");
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        String[] arg = input.split(" ");
        if (input.contains("create") && arg.length == 3) {
            createFile(arg, sc);
        } else if (input.contains("read") && arg.length == 3) {
            readFile(arg, sc);
        } else if (input.contains("info") && arg.length == 3) {
            infoFile(arg, sc);
        } else if (input.contains("help") && arg.length == 1) {
            showHelp();
        } else if (input.contains("help") && arg.length == 2) {
            showCommandHelp(arg, sc);
        } else if (input.contains("exit")) {
            shutDown();
        } else {
            System.out.println("Unknown command");
        }
    }

    private static void showCommandHelp(String[] arg, Scanner sc) {
        switch (arg[1]) {
            case "create":
                System.out.println("create [path] [file-name] - "
                        + "Создает текстовый файл по указанному пути.\n"
                        + "Если путь не существует, вывести соответствующее сообщение.\n"
                        + "Если файл уже существует, вывести запрос на его перезапись\n\n");
                break;
            case "read":
                System.out.println("read [path] [file-name] - "
                        + "Считывает файл по указанному пути и выводит текст в консоль.\n"
                        + "Если указанного пути и/или файла не существует, "
                        + "вывести соответствующее сообщение\n\n");
                break;
            case "info":
                System.out.println("info [path] [file-name] - "
                        + "Выводит краткую информацию по указанному файлу:\n"
                        + "количество символов, строк, слов, дату и время последнего изменения, "
                        + "размер файла\n\n");
                break;
            case "help":
                System.out.println("help - Выводит в консоль все доступные "
                        + "комманды и информацию к ним\n\n"
                        + "help [command] - Выводит в консоль информацию по указанной команде\n\n");
                break;
            case "exit":
                System.out.println("exit - Завершение работы программы");
                break;
            default:
                System.out.println("Unknown command");
        }
    }

    private static void showHelp() {
        System.out.println("create [path] [file-name] - Создает текстовый "
                + "файл по указанному пути.\n"
                + "Если путь не существует, вывести соответствующее сообщение.\n"
                + "Если файл уже существует, вывести запрос на его перезапись\n\n"
                + "read [path] [file-name] - Считывает файл по указанному "
                + "пути и выводит текст в консоль.\n"
                + "Если указанного пути и/или файла не существует, "
                + "вывести соответствующее сообщение\n\n"
                + "info [path] [file-name] - Выводит краткую информацию по указанному файлу:\n"
                + "количество символов, строк, слов, дату и "
                + "время последнего изменения, размер файла\n\n"
                + "help - Выводит в консоль все доступные комманды и информацию к ним\n\n"
                + "help [command] - Выводит в консоль информацию по указанной команде\n\n"
                + "exit - Завершение работы программы");
    }

    private static void infoFile(String[] arg, Scanner sc) {
        Path path = Paths.get(arg[1] + arg[2]);
        if (!Files.exists(Paths.get(arg[1]))) {
            System.out.println("Path does not exists");
            return;
        }
        try {
            int words = 0;
            int lines = 0;
            int chars = 0;
            File file = new File(String.valueOf(path));
            sc = new Scanner(file);
            while (sc.hasNext()) {
                sc.next();
                words = words + 1;
            }
            sc = new Scanner(file);
            while (sc.hasNextLine()) {
                sc.nextLine();
                lines = lines + 1;
            }
            sc = new Scanner(file);
            while (sc.hasNextLine()) {
                chars = chars + sc.nextLine().length();
            }
            long size;
            size = file.length();
            Date date = new Date(file.lastModified());
            System.out.println("Words in file: " + words);
            System.out.println("Lines in file: " + lines);
            System.out.println("Chars in file: " + chars);
            System.out.println("Size of file, bytes: " + size);
            System.out.println("Date of last modify: " + date);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readFile(String[] arg, Scanner sc) {
        Path path = Paths.get(arg[1] + arg[2]);
        if (!Files.exists(Paths.get(arg[1]))) {
            System.out.println("Path does not exists");
            return;
        }
        try {
            System.out.println(Files.readString(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createFile(String[] arg, Scanner sc) {
        Path path = Paths.get(arg[1] + arg[2]);
        if (!Files.exists(Paths.get(arg[1]))) {
            System.out.println("Path does not exists");
            return;
        }
        if (Files.exists(path)) {
            System.out.println("File already exists. Replace? Y / N");
            if (sc.nextLine().equals("N")) {
                return;
            }
        }
        System.out.println("Type your text");
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            writer.write(sc.nextLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void shutDown() {
        System.exit(0);
    }
}
