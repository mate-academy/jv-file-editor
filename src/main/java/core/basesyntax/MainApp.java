package core.basesyntax;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class MainApp {
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
    private static final String WRONG_NUMBER_OF_ARGS_ERROR_MSG =
            "Wrong number of arguments for %s command.\n";
    private static final String ILLEGAL_ARGUMENT_COMMAND =
            "Illegal argument [command]: %s.\n";
    private static final String SAVE_TEXT_TO_FILE_QUESTION =
            "Do you want to save this text to file?";
    private static final String REQUEST_FILE_OVERRIDE_QUESTION =
            "The file already exists. Do you want to override it?";
    private static final String FILE_CREATED_MSG = "File %s created.\n";
    private static final String FAIL_TO_CREATE_FILE_ERROR_MSG =
            "Fail to create file %s.\n";
    private static final String FILE_DELETED_MSG = "File %s deleted.\n";
    private static final String FAIL_TO_DELETE_FILE_ERROR_MSG = "Fail to create file %s.\n";
    private static final String PATH_TO_FILE_DOESNT_EXISTS_MSG = "Path %s doesn't exists. "
            + "Can't create the file.\n";
    private static final String DATA_WRITTEN_TO_FILE_MSG = "Data was written to file.";
    private static final String REQUEST_FILE_PATH_TEXT = "Input file path: ";
    private static final String REQUEST_FILE_NAME_TEXT = "Input file name: ";
    private static final String REG_EXP_MULTI_WHITESPACE = "[\\s]+";
    private static final String FILE_INFO_FORMAT_STRING =
            "File info:\n%20s: %s\n%20s: %s bytes\n%20s: %s\n%20s: %s\n%20s: %s\n";
    private static final String LAST_MODIFIED_PARAM_TITLE = "last modified";
    private static final String SIZE_PARAM_TITLE = "size";
    private static final String NUMBER_OF_STRINGS_PARAM_TITLE = "number of strings";
    private static final String NUMBER_OF_WORDS_PARAM_TITLE = "number of words";
    private static final String NUMBER_OF_CHARS_PARAM_TITLE = "number of chars";
    private static final String FILE_DOESNT_EXIST_ERROR_MSG = "File %s doesn't exist.\n";
    private static final String EMPTY_STRING = "";
    private static final String YES_NO_HINT =
            "(press 'y' for \"yes\" answer, other key for no)";
    private static final String CORRECT_YES_ANSWER = "y";
    private static final String CREATE_COMMAND = "CREATE";
    private static final String READ_COMMAND = "READ";
    private static final String INFO_COMMAND = "INFO";
    private static final String HELP_COMMAND = "HELP";
    private static final String EXIT_COMMAND = "EXIT";
    private static final DateTimeFormatter FORMAT =
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    private static BufferedReader consoleReader =
            new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {
        for (; ; ) {
            String commandText = readLine();
            if (commandText.isEmpty()) {
                continue;
            }
            String[] words = commandText.trim().split(REG_EXP_MULTI_WHITESPACE);
            switch (words[0].toUpperCase()) {
                case CREATE_COMMAND:
                    if (checkNumberOfArguments(CREATE_COMMAND, 2, words)) {
                        create(words[1], words[2], EMPTY_STRING);
                    }
                    continue;
                case READ_COMMAND:
                    if (checkNumberOfArguments(READ_COMMAND, 2, words)) {
                        read(words[1], words[2]);
                    }
                    continue;
                case INFO_COMMAND:
                    if (checkNumberOfArguments(INFO_COMMAND, 2, words)) {
                        info(words[1], words[2]);
                    }
                    continue;
                case HELP_COMMAND:
                    switch (words.length) {
                        case 1:
                            help();
                            continue;
                        case 2:
                            help(words[1].toUpperCase());
                            continue;
                        default:
                            System.out.format(WRONG_NUMBER_OF_ARGS_ERROR_MSG, HELP_COMMAND);
                            help(HELP_COMMAND);
                            continue;
                    }
                case EXIT_COMMAND:
                    if (checkNumberOfArguments(EXIT_COMMAND, 0, words)) {
                        System.exit(0);
                    }
                    continue;
                default:
                    if (askYesNoQuestion(SAVE_TEXT_TO_FILE_QUESTION)) {
                        System.out.print(REQUEST_FILE_PATH_TEXT);
                        String path = readLine();
                        System.out.print(REQUEST_FILE_NAME_TEXT);
                        String fileName = readLine();
                        create(path, fileName, commandText);
                    }
            }
        }
    }

    private static void create(String path, String fileName, String fileContent) {
        File file = new File(path + File.separator + fileName);
        File parentDir = file.getParentFile();
        if (!parentDir.exists()) {
            System.out.format(PATH_TO_FILE_DOESNT_EXISTS_MSG, parentDir.getAbsolutePath());
            return;
        }
        if (file.exists()) {
            if (askYesNoQuestion(REQUEST_FILE_OVERRIDE_QUESTION)) {
                if (file.delete()) {
                    System.out.format(FILE_DELETED_MSG, file.getAbsolutePath());
                } else {
                    System.out.format(FAIL_TO_DELETE_FILE_ERROR_MSG, file.getAbsolutePath());
                    return;
                }
            } else {
                return;
            }
        }
        try {
            if (file.createNewFile()) {
                System.out.format(FILE_CREATED_MSG, file.getAbsolutePath());
            } else {
                System.out.format(FAIL_TO_CREATE_FILE_ERROR_MSG, file.getAbsolutePath());
                return;
            }
            if (!fileContent.isEmpty()) {
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write(fileContent);
                    System.out.println(DATA_WRITTEN_TO_FILE_MSG);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void info(String path, String fileName) {
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

    private static void read(String path, String fileName) {
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

    private static void help(String command) {
        switch (command) {
            case CREATE_COMMAND:
                System.out.println(CREATE_COMMAND_HELP_INFO);
                break;
            case READ_COMMAND:
                System.out.println(READ_COMMAND_HELP_INFO);
                break;
            case INFO_COMMAND:
                System.out.println(INFO_COMMAND_HELP_INFO);
                break;
            case HELP_COMMAND:
                System.out.println(HELP_COMMAND_HELP_INFO);
                break;
            case EXIT_COMMAND:
                System.out.println(EXIT_COMMAND_HELP_INFO);
                break;
            default:
                System.out.format(ILLEGAL_ARGUMENT_COMMAND, command);
                help(HELP_COMMAND);
                return;
        }
        System.out.println();
    }

    private static void help() {
        help(CREATE_COMMAND);
        help(READ_COMMAND);
        help(INFO_COMMAND);
        help(HELP_COMMAND);
        help(EXIT_COMMAND);
    }

    private static boolean checkNumberOfArguments(String command,
                                                  int correctNumber, String[] words) {
        if (words.length - 1 != correctNumber) {
            System.out.format(WRONG_NUMBER_OF_ARGS_ERROR_MSG, command);
            help(command);
            return false;
        }
        return true;
    }

    private static String readLine() {
        try {
            return consoleReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean askYesNoQuestion(String question) {
        try {
            System.out.println(question);
            System.out.println(YES_NO_HINT);
            return consoleReader.readLine().toLowerCase().equals(CORRECT_YES_ANSWER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

