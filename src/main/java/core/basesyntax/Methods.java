package core.basesyntax;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Methods {

    private static final HashMap<String, String> COMMAND_INFO = new HashMap<>();

    static {
        COMMAND_INFO.put("create", "create [path] [file-name]\n"
                + "Создает текстовый файл по указанному пути. "
                + "Если путь не существует, вывести соответствующее сообщение. "
                + "Если файл уже существует, вывести запрос на его перезапись\n");
        COMMAND_INFO.put("read", "read [path] [file-name]\n"
                + "Считывает файл по указанному пути и выводит текст в консоль. "
                + "Если указанного пути и/или файла не существует, "
                + "вывести соответствующее сообщение\n");
        COMMAND_INFO.put("info", "info [path] [file-name]\n"
                + "Выводит краткую информацию по указанному файлу: "
                + "количество символов, строк, слов, "
                + "дату и время последнего изменения, размер файла");
        COMMAND_INFO.put("help", "help [command]\n"
                + "Выводит в консоль информацию по указанной команде\n");
        COMMAND_INFO.put("exit", "exit\n" + "Завершение работы программы\n");
    }

    public void createFile(Path file) {
        if (Files.exists(file)) {
            do {
                System.out.print("File already exists. Would you like to overwrite it? (y/n): ");
            } while (overwriteFile(file, new Scanner(System.in).nextLine()));
        } else {
            try {
                Files.createFile(file);
                System.out.println("File created.");
            } catch (IOException e) {
                System.out.println("Path does not exist!");
            }
        }
    }

    private boolean overwriteFile(Path file, String input) {
        if (input.contentEquals("y")) {
            try (Writer fileWriter = new FileWriter(String.valueOf(file), false)) {
                System.out.println("File was overwritten.");
            } catch (IOException e) {
                System.out.println("Impossible to overwrite!");
            }
        } else if (!input.contentEquals("n")) {
            System.out.println("Wrong input, try again!");
            return true;
        }
        return false;
    }

    public void readFile(Path file) throws IOException {
        List<String> result = Files.readAllLines(file);
        for (String s : result) {
            System.out.println(s);
        }
    }

    public void fileInfo(Path file) throws IOException {
        List<String> strings = Files.readAllLines(file);
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
        long changed = Files.getLastModifiedTime(file).toMillis();
        String pattern = "E, dd MMM yyyy HH:mm:ss z";
        SimpleDateFormat date = new SimpleDateFormat(pattern);
        long size = Files.size(file);
        System.out.println("Symbols quantity: " + symbols + " symbols;"
                + "\nStrings quantity: " + strings.size() + " strings;"
                + "\nWords quantity: " + words + " words;"
                + "\nLast changed: " + date.format(changed) + ";"
                + "\nFile size: " + size + " bytes.");
    }

    public void help() {
        System.out.println("\n" + "help\n"
                + "Выводит в консоль все доступные комманды и информацию к ним\n");
        for (Map.Entry<String, String> entry : COMMAND_INFO.entrySet()) {
            System.out.println(entry.getValue());
        }
    }

    public void helpByCommand(String command) {
        if (!COMMAND_INFO.containsKey(command)) {
            System.out.println("Wrong command!");
        }
        System.out.println(COMMAND_INFO.get(command));
    }

    public void saveText(String text) {
        do {
            System.out.print("No command found. Would you like to save your text? (y/n): ");
        } while (pathChecker(text, new Scanner(System.in).nextLine()));
    }

    private boolean pathChecker(String text, String input) {
        if (input.contentEquals("y")) {
            do {
                System.out.print("Please indicate the name and path for a new file "
                        + "in the next format [path] [name]: ");
            } while (textSaver(text, new Scanner(System.in).nextLine()));
        } else if (!input.contentEquals("n")) {
            System.out.println("Wrong input, try again!");
            return true;
        }
        return false;
    }

    private boolean textSaver(String text, String pathAndName) {
        Pattern pattern2Word = Pattern.compile("^\\[.+]\\s\\[.+]$");
        Matcher matcher2Word = pattern2Word.matcher(pathAndName);
        if (matcher2Word.matches()) {
            String[] answerArray = pathAndName.split(" ");
            Path path = Paths.get(answerArray[0].substring(1, answerArray[0].length() - 1),
                    answerArray[1].substring(1, answerArray[1].length() - 1));
            createFile(path);
            try {
                Files.write(path, text.getBytes());
                System.out.print("Your text has been saved!");
            } catch (IOException e) {
                throw new RuntimeException("File could not be created!");
            }
        } else {
            System.out.println("Wrong input, try again!");
            return true;
        }
        return false;
    }
}
