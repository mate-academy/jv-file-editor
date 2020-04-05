package core.basesyntax;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class FileEditor {

    public static void createFile(String path, String filename) {
        try {
            Path filePath = Paths.get(path);
            if (!Files.exists(filePath)) {
                System.out.println("The path does not exist. Please try again.");
            }
            File file = new File(path + "\\" + filename);
            if (file.createNewFile()) {
                System.out.println("File " + file.getName() + " created ");
            } else {
                System.out.println("The file already exists. Do you want to rewrite it?");
                Scanner scanner = new Scanner(System.in);
                switch (scanner.nextLine()) {
                    case "yes" :
                        file.delete();
                        if (file.createNewFile()) {
                            System.out.println("New file " + file.getName() + " created");
                        }
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    public static void readFile(String path, String filename) {
        Path filePath = Paths.get(path + "\\" + filename);
        if (!Files.exists(filePath)) {
            System.out.println("The file doesn't exist. Please enter another path and name");
            return;
        }
        try {
            List<String> fileContent = Files.readAllLines(filePath);
            String[] lines = new String[fileContent.size()];
            fileContent.toArray(lines);
            for (String s : lines) {
                System.out.println(s);
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    public static void writeFile(String path, String filename, String[] content) {
        try (FileWriter writer = new FileWriter(path + "\\" + filename)) {
            for (String s : content) {
                writer.write(s + " ");
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    public static void getInfo(String path, String filename) {
        Path filePath = Paths.get(path + "\\" + filename);
        if (!Files.exists(filePath)) {
            System.out.println("The file doesn't exist. Try another path and filename");
            return;
        }
        try {
            System.out.println("Last modification date and time: "
                    + Files.getLastModifiedTime(filePath));
            System.out.println("Size: " + Files.size(filePath) + " bytes");
            System.out.println("Number of symbols: " + Files.readAllBytes(filePath).length);
            System.out.println("Number of lines: " + Files.lines(filePath).count());
        } catch (IOException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    public static void getHelp() {
        System.out.println("create [path] [filename] - "
                + "creates a file or overwrites it\n"
                + "read [path] [filename] "
                + "- prints file's content onto console\ninfo [path] [filename] - "
                + " print information about the file\nhelp / help [command] "
                + "- prints description of all commands available or a particular one\n"
                + "exit - exits a program");
    }

    public static void getHelp(String command) {
        switch (command) {
            case "create" :
                System.out.println("create [path] [filename] - "
                        + "creates a file or overwrites it");
                break;
            case "read" :
                System.out.println("read [path] [filename] "
                        + "- prints file's content onto console");
                break;
            case "info" :
                System.out.println("info [path] [filename] - "
                        + "print information about the file");
                break;
            case "help" :
                System.out.println("help / help [command] - prints description of all commands "
                        + "available or a particular one");
                break;
            case "exit" :
                System.out.println("exit - exits the program");
                break;
            default:
                System.out.println("There is no such command. Please, try again.");
                break;
        }
    }

    public static void exitEditor() {
        System.exit(0);
    }
}
