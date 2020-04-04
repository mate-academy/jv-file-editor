package core.basesyntax;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Feel free to remove this class and create your own.
 */
public class HelloWorld {

    public static final Scanner CONSOLE_READER = new Scanner(System.in);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nWrite a command: ");
        String textFromConsole = CONSOLE_READER.nextLine();
        while (!textFromConsole.equals("exit")) {
            if (textFromConsole.equals("help")) {
                help();
            } else {
                if (myPattern("^help+\\s(\\[+)\\w+\\]$",
                        textFromConsole).find()) {
                    String command = textFromConsole
                            .replaceAll("\\w+\\s\\[", "")
                            .replace("]", "");
                    help(command);
                } else if (myPattern("^\\w+\\s(\\[+)\\w+\\]\\s(\\[)\\w+\\.+\\w+\\]$",
                        textFromConsole).find()) {
                    String[] commandDates = textFromConsole
                            .replaceAll("[^a-z\\s.]", "")
                            .split("\\s");
                    switch (commandDates[0]) {
                        case "read":
                            read(commandDates[1], commandDates[2]);
                            break;
                        case "create":
                            create(commandDates[1], commandDates[2]);
                            break;
                        case "info":
                            info(commandDates[1], commandDates[2]);
                            break;
                    }
                } else {
                    System.out.println("You want write this text in to the file(+/-)?");
                    String choise = CONSOLE_READER.next();
                    while (!(choise.equals("+") || choise.equals("-"))) {
                        System.out.println("Please choose (+/-).");
                        choise = CONSOLE_READER.next();
                    }
                    if (choise.equals("+")) {
                        System.out.println("Write Path an File name." +
                                "\nExample: [Path] [FileName.FileType]");
                        String fullPath = scanner.nextLine();
                        if (myPattern("(\\[+)\\w+\\]\\s(\\[)\\w+\\.+\\w+\\]$", fullPath).find()) {
                            String[] pathMass = fullPath
                                    .replaceAll("[^a-z\\s.]", "")
                                    .split("\\s");
                            write(pathMass[0], pathMass[1], textFromConsole);
                            System.out.println("Information has been recorded.");
                        } else {
                            System.out.println("Oops you was make a mistake. " +
                                    "All your text deleted. Next time, be careful)");
                        }
                    } else {
                        System.out.println("Up you to chose.");
                    }
                    //////////write method////////////////////////////////
                }

            }
            System.out.print("\nWrite a command: ");
            textFromConsole = scanner.nextLine();
        }
    }

    public static Matcher myPattern(String regex, String text) {
        Pattern pt = Pattern.compile(regex);
        Matcher mt = pt.matcher(text);
        return mt;
    }

    public static void create(String path, String name) {
        Path filePath = Paths.get(path + File.separator + name);
        try {
            Files.createFile(filePath);
        } catch (FileAlreadyExistsException x) {
            System.out.println("file named " + name + " already exists\n"
                    + "You want delete this file(+/-)?");
            String answer = "";
            do {
                answer = CONSOLE_READER.next();
                switch (answer) {
                    case "+":
                        try {
                            File oldFile = new File(path, name);
                            Files.delete(filePath);
                        } catch (IOException e) {
                            System.out.println("Delete error: " + e.toString());
                        }
                        break;
                    case "-":
                        System.out.println("Ok Bro!");
                        break;
                    default:
                        System.out.println("Unexpected answer.");
                }
            } while (!(answer.equals("+") || answer.equals("-")));
        } catch (IOException e) {
            System.out.println("createFile error: " + e.toString());
        }
    }

    public static void read(String path, String name) {
        File currentFile = new File(path + File.separator + name);
        try {
            String dates = Files.readString(currentFile.toPath());
            System.out.println(dates);
        } catch (IOException e) {
            System.out.println("File does not exist or close for reading");
        }
    }

    public static void write(String path, String name, String text) {
        try (FileWriter fileWriter = new FileWriter(path + File.separator + name)) {
            fileWriter.write(text);
        } catch (IOException e) {
            System.out.println("File does not exist or close for reading");
        }
    }

    public static void info(String path, String name) {
        File file = new File(path + File.separator + name);
        if (file.exists()) {
            try {
                System.out.println("Amount of symbols: "
                        + Files.readString(file.toPath()).replaceAll("\\R", "").length());
                System.out.println("Amount of lines: "
                        + Files.readAllLines(file.toPath()).size());
                System.out.println("Amount of words: "
                        + Files.readString(file.toPath())
                        .replaceAll("\\R", " ").split(" ").length);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Last modified: " + file.lastModified());
            System.out.println("File size: " + file.length());
        } else {
            System.out.println("File Is Not Exist");
        }
    }

    public static void help() {
        System.out.println("Command list:\n" + "\ncreate [path] [file-name]\n" +
                "Creates a text file at the specified path. If the path does not exist,\n" +
                "display the appropriate message. If the" +
                " file already exists, request to overwrite it.\n" +
                "\nread [path] [file-name]\n" +
                "Reads the file at the specified path and displays the text in the console.\n" +
                "If the specified path and / or file does not exist, display the corresponding" +
                " message.\n" + "\ninfo [path] [file-name]\n" +
                "Displays brief information on the specified file:\n" +
                "number of characters, lines, words, date and time of the last change," +
                " file size.\n" + "\nhelp\nDisplays all available commands and information" +
                " to them in the console.\n" + "\nhelp [command]\n" +
                "Displays information on the specified command to the console.\n" +
                "\nexit\n" + "Closing the program.\n\n" +
                "P.S.\nBy writing to the console any text that is not a command\n" +
                "and pressing enter, you will be able to write it to file.");
    }

    public static void help(String command) {
        switch (command) {
            case "create":
                System.out.println("\ncreate [path] [file-name]\n" +
                        "Creates a text file at the specified path. If the path does not exist,\n" +
                        "display the appropriate message. If the" +
                        " file already exists, request to overwrite it.\n");
                break;
            case "read":
                System.out.println("\nread [path] [file-name]\n" +
                        "Reads the file at the specified path and displays the text in" +
                        " the console.\nIf the specified path and / or file does not exist," +
                        " display the corresponding message.\n");
                break;
            case "info":
                System.out.println("\ninfo [path] [file-name]\n" +
                        "Displays brief information on the specified file:\n" +
                        "number of characters, lines, words, date and time of the last change," +
                        " file size.\n");
                break;
            case "help":
                System.out.println("\nhelp\nDisplays all available commands and information" +
                        " to them in the console.\n");
                break;
            case "exit":
                System.out.println("Closing program.");
            default:
                System.out.println("\nBy writing to the console any text that is not a command\n" +
                        "and pressing enter, you will be able to write it to file.");
        }
    }


}
