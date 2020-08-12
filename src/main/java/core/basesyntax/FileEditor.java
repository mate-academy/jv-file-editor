package core.basesyntax;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class FileEditor {
    private Scanner scanner = new Scanner(System.in);
    private boolean work = true;

    public void start() {
        infoMessage();
        while (work) {
            String[] variant = scanner.nextLine().split(" ");
            if (variant.length > 3 || variant.length < 1) {
                infoMessage();
            } else {
                switch (variant[0].toLowerCase()) {
                    case "create":
                        if (variant.length == 3) {
                            create(variant[1], variant[2]);
                        } else {
                            infoMessage();
                        }
                        break;
                    case "read":
                        if (variant.length == 3) {
                            read(variant[1], variant[2]);
                        } else {
                            infoMessage();
                        }
                        break;
                    case "info":
                        if (variant.length == 3) {
                            info(variant[1], variant[2]);
                        } else {
                            infoMessage();
                        }
                        break;
                    case "help":
                        if (variant.length == 2) {
                            help(variant[1]);
                        } else {
                            help();
                        }
                        break;
                    case "exit":
                        exit();
                        break;
                    default:
                        wantToSave();
                }
            }

        }
    }

    private void infoMessage() {
        System.out.println("Please enter valid command with correct amount parameters.\n"
                + "You can get list of available commands by entering 'help'.\n");
    }

    private void create(String path, String filename) {
        try {
            Path newFilePath = Paths.get(path + FileSystems.getDefault().getSeparator() + filename);
            Files.createFile(newFilePath);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void read(String path, String filename) {
        Path filePath = Paths.get(path + FileSystems.getDefault().getSeparator() + filename);
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String currentLine = null;
            while ((currentLine = reader.readLine()) != null) {
                System.out.println(currentLine);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void info(String path, String filename) {
        int charNumber = 0;
        int linesNumber = 0;
        int wordsNumber = 0;
        try {
            Path filePath = Paths.get(path + FileSystems.getDefault().getSeparator() + filename);
            try (BufferedReader reader = Files.newBufferedReader(filePath)) {
                String currentLine = null;
                while ((currentLine = reader.readLine()) != null) {
                    charNumber += currentLine.length();
                    wordsNumber += currentLine.split(" ").length;
                    linesNumber++;
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("File " + filePath + " has following parameters:\n"
                    + "Amount of characters: " + charNumber
                    + "\nNumber of words: " + wordsNumber
                    + "\nNumber of lines: " + linesNumber
                    + "\nSize: " + Files.getAttribute(filePath,"size").toString() + " bytes\n"
                    + "Last modification time: "
                    + Files.getAttribute(filePath, "lastModifiedTime").toString());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void help() {
        System.out.println("create [path] [filename] - creates new file at path\n"
                + "read [path] [filename] - reads file at path\n"
                + "info [path] [filename] - shows info on file at path\n"
                + "help - shows all commands\n"
                + "help [command] - shows more detailed info on the command\n"
                + "exit - exits program");
    }

    private void help(String command) {
        switch (command) {
            case "create":
                System.out.println("create [path] [filename] - creates new file at path\n"
                        + "Creates new file with provided filename at provided path.\n"
                        + "[path] - should have correct delimeters:\n"
                        + "/ - for Linux/Mac\n\\ - for Windows\n"
                        + "If file already exists you will be prompted "
                        + "whether you want to overwrite it");
                break;
            case "read":
                System.out.println("read [path] [filename] - reads file at path\n"
                        + "Outputs content of file in console.\n"
                        + "[path] - should have correct delimeters:\n"
                        + "/ - for Linux/Mac\n\\ - for Windows");
                break;
            case "info":
                System.out.println("info [path] [filename] - shows info on file at path\n"
                        + "Outputs information on provided file like"
                        + "number of characters, lines, words, date "
                        + "and time of last modification and filesize.\n"
                        + "[path] - should have correct delimeters:\n"
                        + "/ - for Linux/Mac\n\\ - for Windows");
                break;
            case "help":
                System.out.println("help - shows all commands\n"
                        + "If entered with second argument and second argument is command name"
                        + " like 'help create' it will provide more detailed info on the command.");
                break;
            case "exit":
                System.out.println("Exits program.");
                break;
            default:
                System.out.println("Malformed request. Please try again");
        }
    }

    private void exit() {
        System.out.println("Goodbye!");
        work = false;
    }

    private void wantToSave() {
        System.out.println("Do you want to save text? [y/n]");
        switch (scanner.nextLine().toLowerCase()) {
            case "y":
                System.out.println("Please enter path to file and filename separated with space");
                String[] input = scanner.nextLine().split(" ");
                if (input.length == 2) {
                    create(input[0], input[1]);
                } else {
                    System.out.println("Please enter path to file and its name");
                }
                break;
            case "n":
                System.out.println("Ok.");
                break;
            default:
                System.out.println("Please try again");
        }

    }
}
