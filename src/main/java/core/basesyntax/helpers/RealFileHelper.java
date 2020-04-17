package core.basesyntax.helpers;

import core.basesyntax.Command;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;

class RealFileHelper implements FileHelper {
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
    private static final String REG_EXP_MULTI_WHITESPACE = "[\\s]+";
    private static final DateTimeFormatter FORMAT =
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    private static final Map<Command, String> HELP_INFO;

    static {
        HELP_INFO = Map.of(Command.CREATE, CREATE_COMMAND_HELP_INFO,
                Command.READ, READ_COMMAND_HELP_INFO,
                Command.INFO, INFO_COMMAND_HELP_INFO,
                Command.HELP, HELP_COMMAND_HELP_INFO,
                Command.EXIT, EXIT_COMMAND_HELP_INFO);
    }

    private RealFileHelper() {
    }

    private static class LazyInstanceHolder {
        private static final RealFileHelper INSTANCE = new RealFileHelper();
    }

    public static RealFileHelper getInstance() {
        return LazyInstanceHolder.INSTANCE;
    }

    public void createFile(String path, String fileName, String fileContent) {
        File file = new File(path + File.separator + fileName);
        File parentDir = file.getParentFile();
        if (!parentDir.exists()) {
            System.out.format("Path %s doesn't exists. Can't create the file.\n",
                    parentDir.getAbsolutePath());
            return;
        }
        if (file.exists() && ! RealConsoleHelper.getInstance()
                .askYesNoQuestion("The file already exists. Do you want to override it?")) {
            return;
        }
        try {
            Files.write(file.toPath(), fileContent.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showFileInfo(String path, String fileName) {
        File file = new File(path + File.separator + fileName);
        if (!file.exists()) {
            System.out.format("File %s doesn't exist.\n", file.getAbsolutePath());
            return;
        }
        String fileContent;
        try {
            fileContent = Files.readString(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int numberOfStrings = fileContent.split("\n").length;
        int numberOfWords = fileContent.split(REG_EXP_MULTI_WHITESPACE).length;
        int numberOfChars = fileContent.length();
        System.out.format("File info:\n%20s: %s\n%20s: %s bytes\n%20s: %s\n%20s: %s\n%20s: %s\n",
                "last modified", Instant.ofEpochMilli(file.lastModified())
                        .atZone(ZoneId.systemDefault()).toLocalDateTime().format(FORMAT),
                "size", file.length(),
                "number of strings", numberOfStrings,
                "number of words", numberOfWords,
                "number of chars", numberOfChars);
    }

    public void showFileContent(String path, String fileName) {
        File file = new File(path + File.separator + fileName);
        if (!file.exists()) {
            System.out.format("File %s doesn't exist.\n", file.getAbsolutePath());
            return;
        }
        try {
            System.out.println(Files.readString(file.toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showCommandHelp(Command command) {
        if (command != Command.UNKNOWN) {
            System.out.println(HELP_INFO.get(command));
            System.out.println();
        } else {
            System.out.format("Illegal argument [command]: %s.\n", command);
            showCommandHelp(Command.HELP);
        }
    }

    public void showCommandHelp() {
        for (Command command : Command.values()) {
            if (command != Command.UNKNOWN) {
                showCommandHelp(command);
            }
        }
    }
}
