package core.basesyntax;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
  Right input: create C:\Users\Lenovo\Desktop\ test.txt
  Wrong input: create C:\Users\Lenovo\Desktop test.txt
 */
public class FileEditor {
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yy HH:mm:ss");
    private static Scanner scanner;

    public static void main(String[] args) {
        try {
            boolean bool = true;
            while (bool) {
                scanner = new Scanner(System.in);
                System.out.println("Write command or text: ");
                String[] input = scanner.nextLine().split(" ");
                switch (getCommand(input)) {
                    case 0:
                        System.out.println("Your input is empty!");
                        break;
                    case 1:
                        create(input[1], input[2]);
                        break;
                    case 2:
                        read(input[1], input[2]);
                        break;
                    case 3:
                        info(input[1], input[2]);
                        break;
                    case 4:
                        help(input[1]);
                        break;
                    case 5:
                        help();
                        break;
                    case 6:
                        bool = false;
                        break;
                    default:
                        save(input);
                        break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            scanner.close();
        }
        System.out.println("Program is finished!");
    }

    private static void create(String path, String fileName) throws IOException {
        if (Files.exists(Paths.get(path))) {
            Path filePath = Paths.get(path + fileName);
            if (Files.exists(filePath)) {
                System.out.println("Do you want to rewrite this file?");
                String answer = scanner.next();
                if (answer.equals("Yes")) {
                    Files.delete(filePath);
                    Files.createFile(filePath);
                    System.out.println("File " + fileName + " overwritten!");
                }
            } else {
                Files.createFile(filePath);
                System.out.println("File " + fileName + " created");
            }
        } else {
            System.out.println("Such path doesn't exist!");
        }
    }

    private static int getCommand(String[] input) {
        int length = input.length;
        return String.join("", input).length() == 0
                ? 0 : input[0].startsWith("create") && length == 3
                ? 1 : input[0].startsWith("read") && length == 3
                ? 2 : input[0].startsWith("info") && length == 3
                ? 3 : input[0].startsWith("help") && length == 2
                ? 4 : input[0].startsWith("help") && length == 1
                ? 5 : input[0].startsWith("exit") && length == 1
                ? 6 : 7;
    }

    private static void help() {
        String[] commands = {"create", "read", "info", "exit"};
        System.out.println("All available commands: ");
        for (String command : commands) {
            help(command);
        }
    }

    private static void help(String command) {
        String answer = command + " -> ";
        switch (command) {
            case "create":
                answer += "Creates a text file at the specified path.";
                break;
            case "read":
                answer += "Reads the file at the specified path "
                        + "and displays the text in the console.";
                break;
            case "info":
                answer += "Displays brief information on the specified file.";
                break;
            case "exit":
                answer += "Program shutdown.";
                break;
            default:
                answer += "Such command doesn't exist.";
                break;
        }
        System.out.println(answer);
    }

    private static void info(String path, String fileName) throws IOException {
        if (exists(path, fileName)) {
            Path pathToFile = Paths.get(path + fileName);
            LocalDateTime date = Instant
                    .ofEpochMilli(Files.getLastModifiedTime(pathToFile).toMillis())
                    .atZone(ZoneId.systemDefault()).toLocalDateTime();
            String text = new String(Files.readAllBytes(pathToFile));
            String builder = "Number of characters - " + text.length() + " characters" + "\n"
                    + "Number of lines - " + Files.lines(pathToFile).count() + " lines" + "\n"
                    + "Number of words - " + text.split(" ").length + " words" + "\n"
                    + "Date and time of the last change - " + date.format(FORMATTER) + "\n"
                    + "Size of file - " + Files.size(pathToFile) + " bytes";
            System.out.println(builder);
        }
    }

    private static void read(String path, String fileName) throws IOException {
        if (exists(path, fileName)) {
            System.out.println(new String(Files.readAllBytes(Paths.get(path + fileName))));
        }
    }

    private static boolean exists(String path, String fileName) {
        if (!Files.exists(Paths.get(path)) || !Files.exists(Paths.get(path + fileName))) {
            System.out.println("Such path or file doesn't exist!");
            return false;
        }
        return true;
    }

    private static void save(String[] text) throws IOException {
        System.out.println("Do you want to save this text to file?");
        if (scanner.next().equals("Yes")) {
            System.out.println("Input path and filename: ");
            String[] input;
            boolean exists;
            do {
                scanner = new Scanner(System.in);
                input = scanner.nextLine().split(" ");
                create(input[0], input[1]);
                exists = Files.exists(Paths.get(String.join("", input)));
                if (!exists) {
                    System.out.println("Enter your path and filename again:");
                }
            } while (!exists);
            write(text, input);
        }
    }

    private static void write(String[] text, String[] path) throws IOException {
        String pathToFile = String.join("", path);
        Files.write(Paths.get(pathToFile), String.join(" ", text).getBytes());
        System.out.println("Text saved in file: " + pathToFile);
    }
}
