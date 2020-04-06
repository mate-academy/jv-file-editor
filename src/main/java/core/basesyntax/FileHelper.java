package core.basesyntax;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class FileHelper {
    private static final String CREATE_COMMAND_HELP_INFO = "CREATE [path] [file-name]\n"
            + "Creates file with specified path and file name. If file exists requests if "
            + "you want to override it.";
    private static final String READ_COMMAND_HELP_INFO = "READ [path] [file-name]\n"
            + "Reads file and displays it's content on a screen.";
    private static final String INFO_COMMAND_HELP_INFO = "INFO [path] [file-name]\n"
            + "Displays brief information on the specified file: number of characters, "
            + "lines, words, date and time of the last change, file size.";
    private static final String HELP_COMMAND_HELP_INFO = "HELP\n"
            + "Displays all available commands and information about them.\n"
            + "HELP [command]\n"
            + "Displays information about the command.";
    private static final String EXIT_COMMAND_HELP_INFO = "EXIT\n"
            + "Completes the program.";
    private static final String ILLEGAL_ARGUMENT_COMMAND =
            "Illegal argument [command]: %s.\n";
    private static final String REQUEST_FILE_OVERRIDE_QUESTION =
            "The file already exists. Do you want to override it?";
    private static final String PATH_TO_FILE_DOESNT_EXISTS_MSG = "Path %s doesn't exists. "
            + "Can't create the file.\n";
    private static final String REG_EXP_MULTI_WHITESPACE = "[\\s]+";
    private static final String FILE_INFO_FORMAT_STRING =
            "File info:\n%20s: %s\n%20s: %s bytes\n%20s: %s\n%20s: %s\n%20s: %s\n";
    private static final String LAST_MODIFIED_PARAM_TITLE = "last modified";
    private static final String SIZE_PARAM_TITLE = "size";
    private static final String NUMBER_OF_STRINGS_PARAM_TITLE = "number of strings";
    private static final String NUMBER_OF_WORDS_PARAM_TITLE = "number of words";
    private static final String NUMBER_OF_CHARS_PARAM_TITLE = "number of chars";
    private static final String FILE_DOESNT_EXIST_ERROR_MSG = "File %s doesn't exist.\n";
    private static final DateTimeFormatter FORMAT =
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    private static FileHelper instance;
    private HashMap<Command, String> helpInfo;

    private FileHelper() {
        helpInfo = new HashMap<>();
        helpInfo.put(Command.CREATE, CREATE_COMMAND_HELP_INFO);
        helpInfo.put(Command.READ, READ_COMMAND_HELP_INFO);
        helpInfo.put(Command.INFO, INFO_COMMAND_HELP_INFO);
        helpInfo.put(Command.HELP, HELP_COMMAND_HELP_INFO);
        helpInfo.put(Command.EXIT, EXIT_COMMAND_HELP_INFO);
    }

    public static FileHelper getInstance() {
        if (instance == null) {
            instance = new FileHelper();
        }
        return instance;
    }

    void createFile(String path, String fileName, String fileContent) {
        File file = new File(path + File.separator + fileName);
        File parentDir = file.getParentFile();
        if (!parentDir.exists()) {
            System.out.format(PATH_TO_FILE_DOESNT_EXISTS_MSG, parentDir.getAbsolutePath());
            return;
        }
        if (file.exists() && ! ConsoleHelper.getInstance()
                .askYesNoQuestion(REQUEST_FILE_OVERRIDE_QUESTION)) {
            return;
        }
        try {
            Files.write(file.toPath(), fileContent.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void showFileInfo(String path, String fileName) {
        File file = new File(path + File.separator + fileName);
        if (!file.exists()) {
            System.out.format(FILE_DOESNT_EXIST_ERROR_MSG, file.getAbsolutePath());
            return;
        }
        int numberOfStrings = 0;
        int numberOfWords = 0;
        int numberOfChars = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String str;
            while ((str = reader.readLine()) != null) {
                numberOfStrings++;
                numberOfChars += str.length();
                numberOfWords += str.split(REG_EXP_MULTI_WHITESPACE).length;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.format(FILE_INFO_FORMAT_STRING,
                LAST_MODIFIED_PARAM_TITLE, Instant.ofEpochMilli(file.lastModified())
                        .atZone(ZoneId.systemDefault()).toLocalDateTime().format(FORMAT),
                SIZE_PARAM_TITLE, file.length(),
                NUMBER_OF_STRINGS_PARAM_TITLE, numberOfStrings,
                NUMBER_OF_WORDS_PARAM_TITLE, numberOfWords,
                NUMBER_OF_CHARS_PARAM_TITLE, numberOfChars);
    }

    void showFileContent(String path, String fileName) {
        File file = new File(path + File.separator + fileName);
        if (!file.exists()) {
            System.out.format(FILE_DOESNT_EXIST_ERROR_MSG, file.getAbsolutePath());
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String str;
            while ((str = reader.readLine()) != null) {
                System.out.println(str);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void showCommandHelp(String command) {
        command = command.toUpperCase();
        for (Command item : Command.values()) {
            if (item.toString().equals(command)) {
                showCommandHelp(item);
                return;
            }
        }
        System.out.format(ILLEGAL_ARGUMENT_COMMAND, command);
        showCommandHelp(Command.HELP);
    }

    void showCommandHelp(Command command) {
        System.out.println(helpInfo.get(command));
        System.out.println();
    }

    void showCommandHelp() {
        for (Command command : Command.values()) {
            showCommandHelp(command);
        }
    }
}
