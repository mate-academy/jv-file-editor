package core.basesyntax;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class FileEditor {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

    private static final HashMap<String, String> COMMAND_INFO = new HashMap<>();

    static {
        COMMAND_INFO.put("create", "creates a text file at the specified path");
        COMMAND_INFO.put("read", "reads a text file at the specified path and "
                + "+ prints the content to the console");
        COMMAND_INFO.put("info", "prints the information about the file: \"\n"
                + "+ \"number of chars, words, lines, date and time of last modifying,"
                + " size of a file");
        COMMAND_INFO.put("help", "prints information about all the commands");
        COMMAND_INFO.put("helpCommand", "prints information about the specified command");
        COMMAND_INFO.put("exit", "exit from the program");
    }

    public void consoleWork() throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter a path and a name of a file you will work with: ");
            String path = scanner.next();
            String filename = scanner.next();
            menu();
            String choice = scanner.next();
            switch (choice) {
                case "create" : {
                    create(path, filename);
                    break;
                }
                case "read" : {
                    System.out.println(read(path, filename));
                    break;
                }
                case "info" : {
                    info(path, filename);
                    break;
                }
                case "help" : {
                    help();
                    break;
                }
                case "helpCommands" : {
                    menu();
                    String command = scanner.next();
                    help(command);
                    break;
                }
                case "exit" : {
                    System.exit(0);
                    break;
                }
                default:
                    System.out.println("Would you like to save the printed text in the file? "
                            + " Enter 'yes' if your answer is positive");
                    String answer = scanner.next();
                    saveInput(path, filename, choice, answer);
            }
        }
    }

    private static void saveInput(String path, String filename, String choice, String answer) {
        File file = new File(path + filename);
        if (answer.equalsIgnoreCase("yes")) {
            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(choice);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void menu() {
        System.out.println("Enter a command: ");
        System.out.println("create\nread\ninfo\nhelp\nhelpCommands"
                + "(information about commands)\nexit");
    }

    public static void create(String path, String name) throws IOException {
        Scanner scanner = new Scanner(System.in);
        File file = new File(path + name);
        if (file.createNewFile()) {
            return;
        }
        System.out.println("Would you like to rewrite the data in a file?"
                + " Enter 'yes' if your answer is positive");
        String answer = scanner.nextLine();
        if (answer.equalsIgnoreCase("yes")) {
            System.out.println("Enter the data you would like to write: ");
            String data = scanner.nextLine();
            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(data);
            } catch (IOException e) {
                throw new RuntimeException("Can't write data into a file because "
                        + "it does not exist", e);
            }
        } else {
            System.out.println("Ok, goodbye...");
        }
    }

    public static String read(String path, String name) {
        try {
            File sourceFile = new File(path + name);
            List<String> list = Files.readAllLines(sourceFile.toPath());
            return list.toString();
        } catch (IOException e) {
            throw new RuntimeException("Can't read data because this file does not exist", e);
        }
    }

    public static void info(String path, String name) {
        int charCount = 0;
        int wordCount = 0;
        int lineCount = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(path + name))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineCount++;
                String[] words = line.split(" ");
                wordCount += words.length;
                for (String word : words) {
                    charCount += word.length();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read data because this file does not exist", e);
        }
        File file = new File(path + name);
        System.out.println("The number of characters: " + charCount);
        System.out.println("The number of words: " + wordCount);
        System.out.println("The number of lines: " + lineCount);
        System.out.println("The date and time of last modifying: "
                + DATE_FORMAT.format(file.lastModified()));
        System.out.println("The size of file in bytes: " + file.length());
    }

    public static void help() {
        System.out.println("Create ---> creates a text file at the specified path ");
        System.out.println("Read ---> reads a text file at the specified path and "
                + "prints the content to the console");
        System.out.println("Info ---> prints the information about the file: "
                + "number of chars, words, lines, date and time of last modifying, size of a file");
        System.out.println("Help ---> prints information about all the commands");
        System.out.println("HelpCommand ---> prints information about the specified command");
        System.out.println("Exit ---> exit from the program");
    }

    public static void help(String command) {
        System.out.println(COMMAND_INFO.get(command));
    }
}
