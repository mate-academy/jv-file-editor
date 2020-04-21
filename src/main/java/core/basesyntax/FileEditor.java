package core.basesyntax;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Scanner;

public class FileEditor {
    private static final String CREATE = "create";
    private static final String READ = "read";
    private static final String INFO = "info";
    private static final String HELP = "help";
    private static final String EXIT = "exit";
    private static final String ENTER_COMMAND = "enter command";
    private static final String WRONG_PARAMETERS_FOR_COMMAND = "wrong parameters for command %s";
    private static final String UNKNOWN_COMMAND = "%s - unknown command";
    private static final String WANT_SAVE = "do you want to save it in file (y/n)?";
    private static final String ENTER_PATH_END_FILENAME
            = "enter path and filename trough the space:";
    private static final String INCORRECT_PATH_OR_FILENAME = "%s - incorrect path and filename";
    private static final String FILE_NOT_FOUND = "file not found";
    private Scanner scanner;
    private String inputString;

    public FileEditor() {
        scanner = new Scanner(System.in);
    }

    public void run() {
        while (true) {
            System.out.println(ENTER_COMMAND);
            inputString = scanner.nextLine();
            String[] args = inputString.replaceAll("\\s+", " ").trim().split(" ");
            handleCommands(args);
        }
    }

    private void handleCommands(String[] args) {
        switch (args[0]) {
            case EXIT:
                System.exit(0);
                break;
            case CREATE:
                if (args.length == 3) {
                    createFile(args[1], args[2], "");
                } else {
                    printWrongParametersForCommand(args[0]);
                }
                break;
            case READ:
                if (args.length == 3) {
                    readFile(args[1], args[2]);
                } else {
                    printWrongParametersForCommand(args[0]);
                }
                break;
            case INFO:
                if (args.length == 3) {
                    readFileInfo(args[1], args[2]);
                } else {
                    printWrongParametersForCommand(args[0]);
                }
                break;
            case HELP:
                Command commandForHelp = null;
                try {
                    commandForHelp = Command.valueOf(args[1].toUpperCase());
                } catch (IllegalArgumentException e) {
                    System.out.println(String.format(UNKNOWN_COMMAND, args[1]));
                } catch (ArrayIndexOutOfBoundsException e) {
                    e.getMessage();
                }
                printHelp(commandForHelp);
                break;
            default:
                System.out.println(String.format(UNKNOWN_COMMAND, inputString));
                System.out.println(WANT_SAVE);
                if (approveOperation()) {
                    String[] pathAndFileName = handleInputPathFilename();
                    createFile(pathAndFileName[0], pathAndFileName[1], inputString);
                }
        }
    }

    private void printHelp(Command command) {
        if (command == null) {
            for (Command cmd : Command.values()) {
                System.out.println(cmd.toString() + " - " + cmd.getHelp());
            }
        } else {
            System.out.println(command.toString() + " - " + command.getHelp());
        }
    }

    private void printWrongParametersForCommand(String arg) {
        System.out.println(String.format(WRONG_PARAMETERS_FOR_COMMAND, arg));
    }

    private String[] handleInputPathFilename() {
        System.out.println(ENTER_PATH_END_FILENAME);
        String[] pathFilename = scanner.nextLine()
                .replaceAll("\\s+", " ").trim().split(" ");
        if (pathFilename.length != 2) {
            System.out.println(String.format(INCORRECT_PATH_OR_FILENAME, pathFilename[0]));
            handleInputPathFilename();
        }
        return pathFilename;
    }

    private boolean approveOperation() {
        String input = scanner.nextLine().trim();
        if (!input.toLowerCase().equals("y") && !input.toLowerCase().equals("n")) {
            System.out.println(String.format(UNKNOWN_COMMAND, input));
        }
        return input.equals("y");
    }

    private void readFileInfo(String path, String fileName) {
        if (!path.endsWith(File.separator)) {
            path += File.separator;
        }
        Path fileNamePath = Paths.get(path + fileName);
        try {
            Date date = new Date(Files.getLastModifiedTime(fileNamePath).toMillis());
            System.out.println(String.format(
                    "Symbols count : %s", Files.readString(fileNamePath).length()));
            System.out.println(String.format(
                    "Rows count  : %s", Files.readAllLines(fileNamePath).size()));
            System.out.println(String.format(
                    "Last modified : %s", date.toString()));
            System.out.println(String.format(
                    "File size : %s bytes", Files.size(fileNamePath)));
        } catch (IOException e) {
            System.out.println(FILE_NOT_FOUND);
        }
    }

    private void readFile(String path, String fileName) {
        if (!path.endsWith(File.separator)) {
            path += File.separator;
        }
        Path fileNamePath = Paths.get(path + fileName);
        String line;
        try (BufferedReader br = Files.newBufferedReader(fileNamePath)) {
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println(FILE_NOT_FOUND);
        }
    }

    private void createFile(String path, String fileName, String content) {
        if (!path.endsWith(File.separator)) {
            path += File.separator;
        }
        Path fileNamePath = Paths.get(path + fileName);
        Path directoriesPath = Paths.get(path);
        try {
            if (Files.notExists(directoriesPath)) {
                Files.createDirectories(directoriesPath);
            }
            Files.createFile(fileNamePath);
            return;
        } catch (FileAlreadyExistsException e) {
            System.out.println("unable to create file, file already exist");
        } catch (IOException e) {
            System.out.println("unable to create file or directory");
            return;
        }
        System.out.println("do you want rewrite it (y/n)?");
        if (!approveOperation()) {
            return;
        }
        writeFile(fileNamePath, content);
    }

    private void writeFile(Path path, String content) {
        try {
            Files.write(path, content.getBytes());
        } catch (IOException e) {
            System.out.println("unable to write file");
        }
    }
}
