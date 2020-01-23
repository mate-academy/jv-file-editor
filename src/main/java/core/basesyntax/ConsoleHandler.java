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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

public class ConsoleHandler {

    private static final Map<String, String> commandInfo = Map.of("create",
            "create [path] [file-name]\nCreate file by specified path.\n", "read",
            "read [path] [file-name]\nRead a file by specified path to the terminal.\n",
            "info", "info [path] [file-name]\nGet short info about specified file.\n",
            "help", "help [command]\n"
                    + "Get info about all available commands or about a specified one.\n",
            "exit", "exit\nSay bye-bye :-)\n");

    public void handleInput() throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            String[] args = input.split(" ");
            String pathName = null;
            String fileName = null;
            if (args.length >= 2) {
                pathName = args[1];
                fileName = args[2];
            }
            switch (args[0]) {
                case "create":
                    createFile(pathName, fileName);
                    break;
                case "read":
                    read(pathName, fileName);
                    break;
                case "info":
                    getInfo(pathName, fileName);
                    break;
                case "help":
                    help(args);
                    break;
                case "exit":
                    System.exit(0);
                    break;
                default:
                    saveText(input);
            }
        }
    }

    private void getInfo(String path, String fileName) throws IOException {
        Path file = Path.of(path + File.separator + fileName);
        System.out.println(Files.readAllBytes(file).length + " symbols");
        System.out.println(Files.readAllLines(file).size() + " lines");
        System.out.println(countWords(Files.readAllLines(file)) + " words");
        BasicFileAttributes attributes = Files.readAttributes(file, BasicFileAttributes.class);
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String dateLastModified = df.format(attributes.lastModifiedTime().toMillis());
        System.out.println(dateLastModified + " last modified");
        System.out.println("Size: " + attributes.size() + " bytes");
    }

    private long countWords(List<String> lines) {
        // this is a hw for lesson 5, so students are not obliged to use Stream API
        return lines.stream()
                .map(line -> Stream.of(line.split(" ")))
                .flatMap(o -> o)
                .filter(str -> str.matches("^[a-zA-Z]+"))
                .count();
    }

    private void help(String[] args) {
        if (args.length >= 2) {
            System.out.println(commandInfo.get(args[1]));
            return;
        }
        for (String info : commandInfo.values()) {
            System.out.println(info);

        }

    }

    private void saveText(String input) throws IOException {
        if (input.isEmpty()) {
            return;
        }
        System.out.println("Save text? Y/N");
        if (askYesNo()) {
            String filePath = askUserFilePath();
            String fileName = askUserFileName();
            createFile(filePath, fileName);
            writeToFile(filePath, fileName, input);
        }
    }

    private boolean askYesNo() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String answer = scanner.next();
            switch (answer) {
                case "Y":
                    return true;
                case "N":
                    return false;
                default:
                    System.out.println("Unknown answer. Please, write Y or N");
            }
        }
    }

    private void writeToFile(String path, String fileName, String content)
            throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(path + File.separator + fileName)) {
            writer.write(content);
        }
    }

    private String askUserFilePath() {
        System.out.println("Please, write path for the new text file:");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private String askUserFileName() {
        System.out.println("Please, write name for the new text file:");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private void read(String path, String fileName) {
        File file = new File(path + File.separator + fileName);
        if (!file.exists()) {
            System.out.println("Warning: no such file or directory");
            return;
        }
        try (BufferedReader reader = new BufferedReader(
                new FileReader(path + File.separator + fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createFile(String pathName, String fileName) {
        try {
            File directory = new File(pathName);
            if (!directory.exists()) {
                System.out.println("Warning: no such directory");
                return;
            }
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
            throw new RuntimeException(e);
        }
    }
}
