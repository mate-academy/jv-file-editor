package core.basesyntax;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;

public class FileEditor {
    private Scanner scanner = new Scanner(System.in);

    public void start() {
        boolean work = true;
        infoMessage();
        while (work) {
            String[] variant = scanner.nextLine().split(" ");
            checkInputLength(variant.length);
            switch (variant[0].toLowerCase()) {
                case "create":
                    if (variant.length != 3) {
                        break;
                    }
                    System.out.print(create(variant[1], variant[2]));
                    break;
                case "read":
                    if (variant.length != 3) {
                        break;
                    }
                    System.out.print(read(variant[1], variant[2]));
                    break;
                case "info":
                    if (variant.length != 3) {
                        break;
                    }
                    System.out.print(info(variant[1], variant[2]));
                    break;
                case "help":
                    if (variant.length == 2) {
                        System.out.println(help(variant[1]));
                        break;
                    }
                    help();
                    break;
                case "exit":
                    work = false;
                    break;
                default:
                    wantToSave();
            }
        }
    }

    private void checkInputLength(int inputLength) {
        if (inputLength > 3 || inputLength < 1) {
            infoMessage();
        }
    }

    private void infoMessage() {
        System.out.println("Please enter valid command with correct amount parameters.\n"
                + "You can get list of available commands by entering 'help'.\n>");
    }

    private String create(String path, String filename) {
        Path newFilePath = Paths.get(path + FileSystems.getDefault().getSeparator() + filename);
        if (Files.exists(newFilePath)) {
            throw new IllegalArgumentException("File already exists");
        }
        try {
            Files.createFile(newFilePath);
            return "File at " + path + FileSystems.getDefault().getSeparator()
                    + filename + " was created\n>";
        } catch (IOException e) {
            throw new IllegalArgumentException("Path does not exist");
        }
    }

    private String read(String path, String filename) {
        StringBuilder buffer = new StringBuilder();
        Path filePath = Paths.get(path + FileSystems.getDefault().getSeparator() + filename);
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                buffer.append(currentLine).append('\n');
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("File not found");
        }
        return buffer.toString();
    }

    private String info(String path, String filename) {
        int charNumber = 0;
        int linesNumber = 0;
        int wordsNumber = 0;
        StringBuilder buffer = new StringBuilder();
        Path filePath = Paths.get(path + FileSystems.getDefault().getSeparator() + filename);
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                charNumber += currentLine.length();
                wordsNumber += currentLine.split(" ").length;
                linesNumber++;
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("File not found");
        }
        try {
            buffer.append("File ").append(filePath)
                    .append(" has following parameters:\n")
                    .append("Amount of characters: ").append(charNumber)
                    .append("\nNumber of words: ").append(wordsNumber)
                    .append("\nNumber of lines: ").append(linesNumber).append("\nSize: ")
                    .append(Files.getAttribute(filePath, "size").toString())
                    .append(" bytes\n").append("Last modification time: ")
                    .append(Files.getAttribute(filePath, "lastModifiedTime").toString())
                    .append("\n");
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot read attribute");
        }
        return buffer.toString();
    }

    private void help() {
        System.out.println("create [path] [filename] - creates new file at path\n"
                + "read [path] [filename] - reads file at path\n"
                + "info [path] [filename] - shows info on file at path\n"
                + "help - shows all commands\n"
                + "help [command] - shows more detailed info on the command\n"
                + "exit - exits program\n");
    }

    private String help(String command) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("create", "create [path] [filename] - creates new file at path\n"
                + "Creates new file with provided filename at provided path.\n"
                + "[path] - should have correct delimeters:\n"
                + "/ - for Linux/Mac\n\\ - for Windows\n"
                + "If file already exists you will be prompted "
                + "whether you want to overwrite it\n");
        hashMap.put("read", "read [path] [filename] - reads file at path\n"
                + "Outputs content of file in console.\n"
                + "[path] - should have correct delimeters:\n"
                + "/ - for Linux/Mac\n\\ - for Windows\n");
        hashMap.put("info", "info [path] [filename] - shows info on file at path\n"
                + "Outputs information on provided file like"
                + "number of characters, lines, words, date "
                + "and time of last modification and filesize.\n"
                + "[path] - should have correct delimeters:\n"
                + "/ - for Linux/Mac\n\\ - for Windows\n");
        hashMap.put("help", "help - shows all commands\n"
                + "If entered with second argument and second argument is command name"
                + " like 'help create' it will provide more detailed info on the command.\n");
        hashMap.put("exit", "Exits program.\n");

        String result = hashMap.get(command);
        return result != null ? result : "Malformed request. Please try again\n";
    }

    private void wantToSave() {
        System.out.println("Do you want to save text? [y/n]\n");
        switch (scanner.nextLine().toLowerCase()) {
            case "y":
                System.out.println("Please enter path to file and filename separated with space\n");
                String[] input = scanner.nextLine().split(" ");
                if (input.length == 2) {
                    create(input[0], input[1]);
                    break;
                }
                System.out.println("Please enter path to file and its name\n");
                break;
            case "n":
                System.out.println("Ok.\n");
                break;
            default:
                System.out.println("Please try again\n");
        }

    }
}
