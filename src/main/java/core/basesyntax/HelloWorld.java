package core.basesyntax;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

/**
 * Feel free to remove this class and create your own.
 */
public class HelloWorld {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String command = scanner.next();
        byte exit = 1;
        while (exit == 1) {
            switch (command) {
                case "create":
                    String path = scanner.next();
                    String fileName = scanner.next();
                    createFile(path, fileName);
                    break;
                case "read":
                    String pathR = scanner.next() + "\\" + scanner.next();
                    readFile(pathR);
                    break;
                case "info":
                    String pathI = scanner.next() + "\\" + scanner.next();
                    fileInfo(pathI);
                    break;
                case "help":
                    if (scanner.hasNext()) {
                        help();
                    } else {
                        help(scanner.next());
                    }
                    break;
                case "exit":
                    exit = 0;
                    System.out.println("Спасибо за внимание");
                    break;
                default:
                    System.out.println("Command can't be read");
            }
        }
    }

    public static void createFile(String path, String fileName) throws IOException {
        if (Files.exists(Paths.get(path))) {
            if (Files.exists(Paths.get(path + "\\" + fileName))) {
                System.out.println("file is exist, do you want to rewrite it?");
                Scanner scanner = new Scanner(System.in);
                if (scanner.next().equals("yes")) {
                    new FileWriter(path + "\\" + fileName, false);
                    System.out.println("File was rewritten");
                }
            } else {
                Files.createFile(Paths.get(path + "\\" + fileName));
                System.out.println("File was created");
            }
        } else {
            System.out.println("Directory is not exists");
        }
    }

    public static void readFile(String path) throws IOException {
        if (Files.exists(Paths.get(path))) {
            List<String> lines = Files.readAllLines(Paths.get(path));
            for (String s : lines) {
                System.out.println(s);
            }
        } else {
            System.out.println("File not existed");
        }
    }

    public static void fileInfo(String path) throws IOException {
        long w = 1;
        long ch = 0;
        List<String> lines = Files.readAllLines(Paths.get(path));
        if (lines.isEmpty()) {
            w = 0;
        }
        for (String s : lines) {
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == ' ') {
                    w++;
                }
                ch++;
            }
        }
        System.out.println("lines = " + lines.size() + "\t words = " + w + "\t chars = " + ch);
        System.out.println("Last modified: " + Files.getLastModifiedTime(Paths.get(path)));
        System.out.println("size is " + Files.size(Paths.get(path)) + " bytes");
    }

    public static void help() {
        System.out.println("create [path] [file-name]\n"
                + "Создает текстовый файл по указанному пути. Если путь не "
                + "существует, вывести соответствующее сообщение."
                + " Если файл уже существует, вывести запрос на "
                + "его перезапись\n read [path] [file-name]\n"
                + "Считывает файл по указанному пути и выводит текст в консоль."
                + " Если указанного пути и/или файла не "
                + "существует, вывести соответствующее сообщение\n"
                + "info [path] [file-name]\n"
                + "Выводит краткую информацию по указанному файлу: количество символов, "
                + "строк, слов, дату и время последнего изменения, размер файла\n"
                + "help\n"
                + "Выводит в консоль все доступные комманды и информацию к ним\n"
                + "help [command]\n"
                + "Выводит в консоль информацию по указанной команде\n"
                + "exit\n + Завершение работы программы\n");
    }

    public static void help(String command) {
        switch (command) {
            case "create":
                System.out.println("create [path] [file-name]\n"
                        + "Создает текстовый файл по указанному пути. Если путь "
                        + "не существует, вывести соответствующее сообщение."
                        + " Если файл уже существует, вывести запрос на его перезапись");
                break;
            case "read":
                System.out.println("read [path] [file-name]\n"
                        + "Считывает файл по указанному пути и выводит текст в консоль."
                        + " Если указанного пути и/или файла не "
                        + "существует, вывести соответствующее сообщение\n");
                break;
            case "info":
                System.out.println("info [path] [file-name]\n"
                        + "Выводит краткую информацию по указанному "
                        + "файлу: количество символов, строк, слов, "
                        + "дату и время последнего изменения, размер файла\n");
                break;
            case "help":
                System.out.println("help\n"
                        + "Выводит в консоль все доступные комманды и информацию к ним\n"
                        + "help [command]\n"
                        + "Выводит в консоль информацию по указанной команде\n");
                break;
            case "exit":
                System.out.println("Выходит с програмы");
                break;
            default:
                System.out.println("command is not declared");
        }
    }
}
