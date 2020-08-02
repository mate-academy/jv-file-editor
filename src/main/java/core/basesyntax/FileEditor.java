package core.basesyntax;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Scanner;

public class FileEditor {
    Scanner scanner = new Scanner(System.in);

    public void startWork() {
        System.out.println("This is my small console app for creating/editing text files\n"
                + "Here is the list of available operations:\n"
                + "create path filename     will create a new file in path directory\n"
                + "read path filename       will read file from path directory\n"
                + "info path filename       will show you a short brief about file\n"
                + "help                     will show all available commands\n"
                + "help command             will tell you a bit more about chosen command\n"
                + "exit                     will finish this program\n\n"
                + "Type your command to start working with app\n"
                + "IMPORTANT: you don't need to type .txt at the end of the filename\n");

        String input = scanner.nextLine();
        String[] inputWords = input.split(" ");

        if (inputWords.length == 1) {
            switch (inputWords[0].toLowerCase()) {
                case ("help") :
                    help();
                    break;
                case ("exit") :
                    System.out.println("Thank you for using my app!\n"
                            + "Have a nice day!");
                    break;
                default :
                    writeTextToFile(input);
            }
        } else if (inputWords.length == 2) {
            if (inputWords[0].toLowerCase().equals("help")) {
                commandHelp(inputWords[1]);
            } else {
                writeTextToFile(input);
            }
        } else if (inputWords.length == 3) {
            switch (inputWords[0].toLowerCase()) {
                case ("create") :
                    createFile(inputWords[1],inputWords[2]);
                    break;
                case ("read") :
                    readFile(inputWords[1], inputWords[2]);
                    break;
                case ("info") :
                    fileInfo(inputWords[1], inputWords[2]);
                    break;
                default :
                    writeTextToFile(input);
                    break;
            }
        } else {
            writeTextToFile(input);
        }

    }

    private void createFile(String filepath, String filename) {
        Path directoryPath = Paths.get(filepath);
        Path filePath = Paths.get(filepath + File.separator + filename + ".txt");
        if (!Files.exists(directoryPath)) {
            System.out.println("This directory does not exist. Create this directory? (Y/N)");
            String answer = scanner.nextLine();
            if (answer.toLowerCase().equals("y")) {
                try {
                    Files.createDirectory(directoryPath);
                } catch (IOException e) {
                    System.out.println("Problem with directory creation");
                    returnToBeginning();
                }
            } else {
                returnToBeginning();
            }
        }
        if (Files.exists(filePath)) {
            System.out.println("File is already exists. Override file? (Y/N)");
            String answer = scanner.nextLine();
            if (answer.toLowerCase().equals("y")) {
                try {
                    Files.delete(filePath);
                    Files.createFile(filePath);
                } catch (IOException e) {
                    System.out.println("Some problems with overriding");
                    returnToBeginning();
                }
                System.out.println("File successfully created!");
            } else {
                returnToBeginning();
            }
        } else {
            try {
                Files.createFile(filePath);
            } catch (IOException e) {
                System.out.println("Some problems with file creation");
            }
            System.out.println("File successfully created!");
        }

    }

    private void readFile(String filepath, String filename) {
        Path filePath = Paths.get(filepath + File.separator + filename + ".txt");

        try {
            List<String> lines = Files.readAllLines(filePath);
            for (String line : lines) {
                System.out.println(line);
            }
            returnToBeginning();
        } catch (IOException e) {
            System.out.println("No such file or directory");
            returnToBeginning();
        }

    }

    private void fileInfo(String filepath, String filename) {
        Path filePath = Paths.get(filepath + File.separator + filename + ".txt");
        try {
            List<String> lines = Files.readAllLines(filePath);
            int symbolCounter = 0;
            int wordCounter = 0;
            for (String line : lines) {
                String[] words = line.split(" ");
                wordCounter += words.length;
                for (String word : words) {
                    char[] chars = word.toCharArray();
                    symbolCounter += chars.length;
                }
            }
            BasicFileAttributes attributes
                    = Files.readAttributes(filePath,BasicFileAttributes.class);

            System.out.println("Number of lines: " + lines.size());
            System.out.println("Number of words: " + wordCounter);
            System.out.println("Number of symbols: " + symbolCounter);
            System.out.println("File creation date: " + attributes.creationTime());
            System.out.println("Last access time: " + attributes.lastAccessTime());
            System.out.println("Last modified time: " + attributes.lastModifiedTime());

            returnToBeginning();

        } catch (IOException e) {
            System.out.println("Wrong file or directory");
            returnToBeginning();
        }

    }

    private void help() {
        startWork();
    }

    private void commandHelp(String command) {
        switch (command.toLowerCase()) {
            case("create") :
                System.out.println("This command will create a new text file\n"
                        + "Type create, than filepath, than filename (split it with spaces)\n"
                        + "You dont need to type .txt at the end of the file");
                returnToBeginning();
                break;
            case("read") :
                System.out.println("This command will read selected file\n"
                        + "Type read, than filepath, than filename (split it with spaces)\n"
                        + "You dont need to type .txt at the end of the file");
                returnToBeginning();
                break;
            case("info") :
                System.out.println("This command give you an info about file\n"
                        + "Type info, than filepath, than filename (split it with spaces)\n"
                        + "You dont need to type .txt at the end of the file");
                returnToBeginning();
                break;
            default:
                System.out.println("No such command available");
                returnToBeginning();
                break;

        }
    }

    private void writeTextToFile(String input) {
        System.out.println("Your input does not match with any command\n"
                + "Do you want to write your input to new text file? (Y/N)");
        String answer = scanner.nextLine();
        if (answer.toLowerCase().equals("y")) {
            System.out.println("Type correct file directory");
            String directory = scanner.nextLine();
            System.out.println("Type filename");
            String filename = scanner.nextLine();
            createFile(directory, filename);
            Path filePath = Paths.get(directory + File.separator + filename + ".txt");
            try {
                Files.writeString(filePath, input);
                returnToBeginning();
            } catch (IOException e) {
                System.out.println("Some problems here, dude");
            }
        } else {
            returnToBeginning();
        }
    }

    private void returnToBeginning() {
        System.out.println("Return to beginning? (Y/N)");
        String answer = scanner.nextLine();
        switch (answer.toLowerCase()) {
            case("y"):
                startWork();
                break;
            case ("n"):
                System.out.println("Thank you for using my app!\n"
                        + "Have a nice day!");
                break;
            default:
                System.out.println("Wrong input. Just Y or N, dude");
                returnToBeginning();
                break;
        }
    }
}
