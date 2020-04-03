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
 * Right input: create C:\Users\Lenovo\Desktop\ test.txt
 * Wrong input: create C:\Users\Lenovo\Desktop test.txt
 */
public class Command {
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yy HH:mm:ss");
    private Scanner scanner;

    public Command(Scanner scanner) {
        this.scanner = scanner;
    }

    public void createFile(String path, String fileName) throws IOException {
        if (Files.exists(Paths.get(path))) {
            Path filePath = Paths.get(path + fileName);
            if (Files.exists(filePath)) {
                System.out.println("Do you want to rewrite this file?");
                String answer = scanner.next();
                if (answer.equals("Yes")) {
                    writeToFile("",path + fileName);
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

    public void callHelp() {
        String[] commands = {"create", "read", "info", "exit"};
        System.out.println("All available commands: ");
        for (String command : commands) {
            callHelp(command);
        }
    }

    public void callHelp(String command) {
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

    public void getInfo(String path, String fileName) throws IOException {
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

    public void readFile(String path, String fileName) throws IOException {
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

    public void saveText(String[] text) throws IOException {
        System.out.println("Do you want to save this text to file?");
        if (scanner.next().equals("Yes")) {
            System.out.println("Input path and filename: ");
            String[] input;
            boolean exists;
            do {
                scanner = new Scanner(System.in);
                input = scanner.nextLine().split(" ");
                createFile(input[0], input[1]);
                exists = Files.exists(Paths.get(String.join("", input)));
                if (!exists) {
                    System.out.println("Enter your path and filename again:");
                }
            } while (!exists);
            String pathToFile = String.join("",input);
            writeToFile(String.join(" ",text), pathToFile);
            System.out.println("Text saved in file: " + pathToFile);
        }
    }

    private static void writeToFile(String text, String path) throws IOException {
        Files.write(Paths.get(path), text.getBytes());
    }
}
