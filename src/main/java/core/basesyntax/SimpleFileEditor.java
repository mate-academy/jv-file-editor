package core.basesyntax;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Feel free to remove this class and create your own.
 */
public class SimpleFileEditor {
    private static final String CREATE = "create";
    private static final String READ = "read";
    private static final String INFO = "info";
    private static final String HELP = "help";
    private static final String EXIT = "exit";
    private static final String YES = "yes";
    private static final String NOT = "not";
    private static final String STOP = "stop"; // for test

    public void readingCommand(String stop) {
        String command = "";
        while (!stop.equals(STOP) && !command.equals(EXIT)) {
            Scanner scanner = new Scanner(System.in);
            if (scanner.hasNext()) {
                command = scanner.nextLine();
                String[] wordsCommand = command.split(" ");
                switch (wordsCommand[0]) {
                    case CREATE:
                        createFile(wordsCommand);
                        break;
                    case READ:
                        readFile(wordsCommand);
                        break;
                    case INFO:
                        infoAboutFile(wordsCommand);
                        break;
                    case HELP:
                        help(wordsCommand);
                        break;
                    case EXIT :
                        break;
                    default:
                        savingText(command);
                }
            }
        }

    }

    private void savingText(String command) {
        System.out.println("Do you want to save the text to a file?");
        String answer = "";
        while (!answer.equals(YES)) {
            Scanner scanner = new Scanner(System.in);
            answer = scanner.nextLine();
            if (answer.equals("yes")) {
                try {
                    Files.writeString(Paths.get("text.txt"), command);
                    System.out.println("Text saved in file - text.txt");
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (answer.equals(NOT)) {
                return;
            } else {
                System.out.println("Incorrectly answer!");
            }
        }
    }

    private void help(String[] wordsCommand) {
        if (validate(wordsCommand)) {
            if (wordsCommand.length == 1) {
                writeInfoAboutCommand(CREATE);
                writeInfoAboutCommand(READ);
                writeInfoAboutCommand(INFO);
                writeInfoAboutCommand(EXIT);
            } else {
                String command = wordsCommand[1].substring(1, wordsCommand[1].length() - 1);
                writeInfoAboutCommand(command);
            }
        }
    }

    private void writeInfoAboutCommand(String command) {
        switch (command) {
            case CREATE:
                System.out.println("create : to create file, need to specify [path] [file-name]!");
                break;
            case READ:
                System.out.println("read : to read file, need to specify [path] [file-name]!");
                break;
            case INFO:
                System.out.println("info : Displays brief information on the specified file:"
                        + "number of characters, lines, words, date and time of"
                        + "the last change, file size");
                break;
            case EXIT:
                System.out.println("exit : Exit from app!");
                break;
            default:
                System.out.println("Unknown command!");
                break;
        }
    }

    private void infoAboutFile(String[] wordsCommand) {
        if (validate(wordsCommand)) {
            String pathString = wordsCommand[1].substring(1, wordsCommand[1].length() - 1);
            String fileNameString = wordsCommand[2].substring(1, wordsCommand[2].length() - 1);
            Path path = Paths.get(pathString + "\\" + fileNameString);
            StringBuilder text = new StringBuilder();
            int counterLine = 0;
            try (BufferedReader bufferedReader = Files.newBufferedReader(path)) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    text.append(line).append(' ');
                    counterLine++;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String[] words = text.toString().split(" ");
            System.out.println("Quantity characters : " + text.length());
            System.out.println("Quantity words : " + words.length);
            System.out.println("Quantity lines : " + counterLine);
            try {
                System.out.println("Last modified : " + Files.getLastModifiedTime(path));
                System.out.println(String.format("File size : %d bite.", Files.size(path)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void createFile(String[] wordsCommand) {
        if (validate(wordsCommand)) {
            String pathString = wordsCommand[1].substring(1, wordsCommand[1].length() - 1);
            String fileNameString = wordsCommand[2].substring(1, wordsCommand[2].length() - 1);
            Path path = Paths.get(pathString + "\\" + fileNameString);
            if (!Files.exists(Paths.get(pathString))) {
                System.out.println("Path not exists!");
                return;
            }
            if (Files.exists(path)) {
                System.out.println("File exists! Overwrite ?");
                String answer = "";
                while (!answer.equals(YES)) {
                    Scanner scanner = new Scanner(System.in);
                    answer = scanner.nextLine();
                    if (answer.equals("yes")) {
                        try {
                            Files.delete(path);
                            Files.createFile(path);
                            return;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (answer.equals(NOT)) {
                        return;
                    } else {
                        System.out.println("Incorrectly answer!");
                    }
                }
            }
            try {
                Files.createFile(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void readFile(String[] wordsCommand) {
        if (validate(wordsCommand)) {
            String pathString = wordsCommand[1].substring(1, wordsCommand[1].length() - 1);
            String fileNameString = wordsCommand[2].substring(1, wordsCommand[2].length() - 1);
            Path path = Paths.get(pathString + "\\" + fileNameString);
            if (!Files.exists(Paths.get(pathString))) {
                System.out.println("Path not exists!");
                return;
            }
            if (!Files.exists(path)) {
                System.out.println("File not exists!");
            } else {
                try (BufferedReader bufferedReader = Files.newBufferedReader(path)) {
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private boolean validate(String[] wordsCommand) {
        if (wordsCommand[0].equals(CREATE) || wordsCommand[0].equals(READ)
                || wordsCommand[0].equals(INFO)) {
            if (wordsCommand.length != 3) {
                System.out.println(String.format(
                        "Invalid number of words in command %d, should be 3!",
                        wordsCommand.length));
                return false;
            }
            if (wordsCommand[1].length() > 0 && (wordsCommand[1].charAt(0) != '['
                    || wordsCommand[1].charAt(wordsCommand[1].length() - 1) != ']')) {
                System.out.println(String.format(
                        "Incorrectly specified path %s, should be [path]!",
                        wordsCommand[1]));
                return false;
            }
            if (wordsCommand[2].length() > 0 && (wordsCommand[2].charAt(0) != '['
                    || wordsCommand[2].charAt(wordsCommand[2].length() - 1) != ']')) {
                System.out.println(String.format(
                        "Incorrectly specified file-name %s, should be [file-name]!",
                        wordsCommand[2]));
                return false;
            }
        }
        if (wordsCommand[0].equals(HELP)) {
            if (wordsCommand.length > 2) {
                System.out.println(String.format(
                        "Invalid number of words in command %d, should be 1 or 2!",
                        wordsCommand.length));
                return false;
            }
            if (wordsCommand.length == 2 && wordsCommand[1].length() > 0
                    && ((wordsCommand[1].charAt(0) != '[')
                    || wordsCommand[1].charAt(wordsCommand[1].length() - 1) != ']')) {
                System.out.println(String.format(
                        "Incorrectly specified command %s, should be [command]!",
                        wordsCommand[1]));
                return false;
            }
        }
        return true;
    }
}
