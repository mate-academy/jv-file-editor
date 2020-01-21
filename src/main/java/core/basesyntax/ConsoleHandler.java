package core.basesyntax;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

public class ConsoleHandler {

    private static final Map<String, String> commandInfo = new LinkedHashMap<>();

    static {
        commandInfo.put("create", "create [path] [file-name]\nCreate file by specified path.\n");
        commandInfo.put("read",
                "read [path] [file-name]\nRead a file by specified path to the terminal.\n");
        commandInfo.put("info", "info [path] [file-name]\nGet short info about specified file.\n");
        commandInfo.put("help", "help [command]\n"
                + "Get info about all available commands or about a specified one.\n");
        commandInfo.put("exit", "exit\nSay bye-bye :-)\n");
    }

    public void handleInput() throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            String[] split = input.split(" ");
            switch (split[0]) {
                case "create":
                    create(split[1], split[2]);
                    break;
                case "read":
                    read(split[1], split[2]);
                    break;
                case "info":
                    info(split[1], split[2]);
                    break;
                case "help":
                    if (split.length >= 2) {
                        help(split[1]);
                    } else {
                        help();
                    }
                    break;
                case "exit":
                    System.exit(0);
                    break;
                default:
                    saveText(input);
            }
        }
    }

    private void info(String path, String fileName) throws IOException {
        Path file = Path.of(path + File.separator + fileName);
        System.out.println(Files.readAllBytes(file).length + " symbols");
        System.out.println(Files.readAllLines(file).size() + " lines");
        System.out.println(countWords(Files.readAllLines(file)) + " words");
        Calendar calendar = Calendar.getInstance();
        BasicFileAttributes attributes = Files.readAttributes(file, BasicFileAttributes.class);
        calendar.setTimeInMillis(attributes.lastModifiedTime().toMillis());

        System.out.println(calendar.getTime() + " last modified");
        System.out.println("Size: " + attributes.size() + " bytes");
    }

    private long countWords(List<String> lines) {
        //this is a hw for lesson 5, so students are not obliged to use Stream API
        return lines.stream()
                .map(line -> Stream.of(line.split(" ")))
                .flatMap(o -> o)
                .filter(str -> str.matches("^[a-zA-Z]+"))
                .count();
    }

    private void help() {
        for (String info : commandInfo.values()) {
            System.out.println(info);
        }
    }

    private void help(String commandName) {
        System.out.println(commandInfo.get(commandName));
    }

    private void saveText(String input) throws IOException {
        if (input.isEmpty()) {
            return;
        }
        System.out.println("Save text? Y/N");
        if (askYesNo()) {
            String[] saveFilePath = getSaveFilePath();
            create(saveFilePath[0], saveFilePath[1]);
            writeToFile(saveFilePath[0], saveFilePath[1], input);
        }
    }

    private boolean askYesNo() {
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.next();
        switch (answer) {
            case "Y":
                return true;
            case "N":
                return false;
            default:
                System.out.println("Unknown answer. Please, write Y or N");
                askYesNo();
        }
        return false;
    }

    private void writeToFile(String path, String fileName, String input)
            throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(path + File.separator + fileName)) {
            writer.write(input);
        }
    }

    private String[] getSaveFilePath() {
        System.out.println("Please, write path and name for the new text file:");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        return input.split(" ");
    }

    private void read(String path, String fileName) {
        try (BufferedReader reader = new BufferedReader(
                new FileReader(path + File.separator + fileName))) {
            String line = reader.readLine();
            while (line != null) {
                System.out.println(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            System.out.println("Warning: no such file or directory");
        }
    }

    private void create(String pathName, String fileName) {
        try {
            File file = new File(pathName + File.separator + fileName);
            if (file.exists()) {
                System.out.println("File " + fileName + " at " + pathName + " already exists.\n"
                        + "Rewrite? Y/N");
                if (askYesNo()) {
                    file.delete();
                }
            }
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("Warning: no such file or directory");
        }
    }
}
