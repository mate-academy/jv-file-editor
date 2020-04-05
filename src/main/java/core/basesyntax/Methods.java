package core.basesyntax;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Methods {

    private static Scanner scanner;

    protected static void createFile(String pathUri, String filename) {
        Path path = Paths.get(pathUri);
        Path file = Paths.get(pathUri, filename);
        if (Files.exists(file)) {
            boolean localDecision = true;
            do {
                System.out.print("File already exists. Would you like to overwrite it? (y/n): ");
                scanner = new Scanner(System.in);
                String answer = scanner.nextLine();
                if (answer.contentEquals("n")) {
                    localDecision = false;
                } else if (answer.contentEquals("y")) {
                    try (Writer fileWriter = new FileWriter(String.valueOf(file), false)) {
                        System.out.println("File was overwritten.");
                    } catch (IOException e) {
                        System.out.println("Path does not exist!");
                    }
                    localDecision = false;
                } else {
                    System.out.println("Wrong input, try again!");
                }
            } while (localDecision);
        } else {
            try {
                Files.createFile(file);
                System.out.println("File created.");
            } catch (IOException e) {
                System.out.println("Path does not exist!");
            }
        }
    }

    protected static void readFile(String path, String filename) throws IOException {
        List<String> result = Files.readAllLines(Paths.get(path, filename));
        for (String s : result) {
            System.out.println(s);
        }
    }

    protected static void fileInfo(String path, String filename) throws IOException {
        List<String> strings = Files.readAllLines(Paths.get(path, filename));
        int symbols = 0;
        int words = 0;
        String[] wordsArray;
        for (String s : strings) {
            wordsArray = s.split(" ");
            words += wordsArray.length;
            for (String s1 : wordsArray) {
                symbols += s1.length();
            }
        }
        long changed = Files.getLastModifiedTime(Paths.get(path, filename)).toMillis();
        String pattern = "E, dd MMM yyyy HH:mm:ss z";
        SimpleDateFormat date = new SimpleDateFormat(pattern);
        long size = Files.size(Paths.get(path, filename));
        System.out.println("Symbols quantity: " + symbols + " symbols;"
                + "\nStrings quantity: " + strings.size() + " strings;"
                + "\nWords quantity: " + words + " words;"
                + "\nLast changed: " + date.format(changed) + ";"
                + "\nFile size: " + size + " bytes.");
    }

    protected static void help() {
        System.out.println("create [path] [file-name]\n"
                + "Создает текстовый файл по указанному пути. "
                + "Если путь не существует, вывести соответствующее сообщение. "
                + "Если файл уже существует, вывести запрос на его перезапись\n"
                + "\n"
                + "read [path] [file-name]\n"
                + "Считывает файл по указанному пути и выводит текст в консоль. "
                + "Если указанного пути и/или файла не существует, "
                + "вывести соответствующее сообщение\n"
                + "\n"
                + "info [path] [file-name]\n"
                + "Выводит краткую информацию по указанному файлу: "
                + "количество символов, строк, слов, "
                + "дату и время последнего изменения, размер файла\n"
                + "\n"
                + "help\n"
                + "Выводит в консоль все доступные комманды и информацию к ним\n"
                + "\n"
                + "help [command]\n"
                + "Выводит в консоль информацию по указанной команде\n"
                + "\n"
                + "exit\n"
                + "Завершение работы программы");
    }

    protected static void helpByCommand(String command) {
        switch (command) {
            case "help":
                System.out.println("help [command]\n"
                        + "Выводит в консоль все доступные комманды и информацию к ним\n");
                break;
            case "create":
                System.out.println("create [path] [file-name]\n"
                        + "Создает текстовый файл по указанному пути. "
                        + "Если путь не существует, вывести соответствующее сообщение. "
                        + "Если файл уже существует, вывести запрос на его перезапись\n");
                break;
            case "read":
                System.out.println("read [path] [file-name]\n"
                        + "Считывает файл по указанному пути и выводит текст в консоль. "
                        + "Если указанного пути и/или файла не существует, "
                        + "вывести соответствующее сообщение\n");
                break;
            case "info":
                System.out.println("info [path] [file-name]\n"
                        + "Выводит краткую информацию по указанному файлу: "
                        + "количество символов, строк, слов, "
                        + "дату и время последнего изменения, размер файла\n");
                break;
            case "exit":
                System.out.println("exit\n" + "Завершение работы программы");
                break;
            default:
                System.out.println("Wrong command!");
        }
    }

    protected static void saveText(String string) {
        boolean decision = true;
        do {
            scanner = new Scanner(System.in);
            System.out.print("No command found. Would you like to save your text? (y/n): ");
            String answer = scanner.nextLine();
            if (answer.contentEquals("y")) {
                scanner = new Scanner(System.in);
                System.out.print("Please indicate the name and path for a new file "
                        + "in the next format [path] [name]: ");
                Pattern pattern2Word = Pattern.compile("^\\[.+]\\s\\[.+]$");
                String pathAndName = scanner.nextLine();
                Matcher matcher2Word = pattern2Word.matcher(pathAndName);
                boolean localDecision = true;
                do {
                    if (matcher2Word.matches()) {
                        String[] answerArray = pathAndName.split(" ");
                        String path = answerArray[0].substring(1, answerArray[0].length() - 1);
                        String name = answerArray[1].substring(1, answerArray[1].length() - 1);
                        createFile(path, name);
                        try {
                            Files.write(Paths.get(path, name), string.getBytes());
                            System.out.print("Your text has been saved!");
                        } catch (IOException e) {
                            throw new RuntimeException("File could not be created!");
                        }
                        decision = false;
                        localDecision = false;
                    } else {
                        System.out.println("Wrong input, try again!");
                    }
                } while (localDecision);
            } else if (answer.contentEquals("n")) {
                decision = false;
            } else {
                System.out.println("Wrong input, try again!");
            }
        } while (decision);
    }
}
