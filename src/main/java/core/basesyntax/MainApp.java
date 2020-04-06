package core.basesyntax;

public class MainApp {
    private static final String EMPTY_STRING = "";
    private static final String SAVE_TEXT_TO_FILE_QUESTION =
            "Do you want to save this text to file?";
    private static final String REQUEST_FILE_PATH_TEXT = "Input file path: ";
    private static final String REQUEST_FILE_NAME_TEXT = "Input file name: ";
    private static final String WRONG_NUMBER_OF_ARGS_ERROR_MSG =
            "Wrong number of arguments for %s command.\n";
    private static final String REG_EXP_MULTI_WHITESPACE = "[\\s]+";
    private static final String CREATE_COMMAND = "CREATE";
    private static final String READ_COMMAND = "READ";
    private static final String INFO_COMMAND = "INFO";
    private static final String HELP_COMMAND = "HELP";
    private static final String EXIT_COMMAND = "EXIT";

    public static void main(String[] args) {
        for (; ; ) {
            String commandText = ConsoleHelper.getInstance().readNotEmptyLine();
            if (commandText.isEmpty()) {
                continue;
            }
            String[] words = commandText.trim().split(REG_EXP_MULTI_WHITESPACE);
            switch (words[0].toUpperCase()) {
                case CREATE_COMMAND:
                    if (checkNumberOfArguments(Command.CREATE, 2, words)) {
                        FileHelper.getInstance().createFile(words[1], words[2], EMPTY_STRING);
                    }
                    continue;
                case READ_COMMAND:
                    if (checkNumberOfArguments(Command.READ, 2, words)) {
                        FileHelper.getInstance().showFileContent(words[1], words[2]);
                    }
                    continue;
                case INFO_COMMAND:
                    if (checkNumberOfArguments(Command.INFO, 2, words)) {
                        FileHelper.getInstance().showFileInfo(words[1], words[2]);
                    }
                    continue;
                case HELP_COMMAND:
                    startHelpCommand(words);
                    continue;
                case EXIT_COMMAND:
                    if (checkNumberOfArguments(Command.EXIT, 0, words)) {
                        System.exit(0);
                    }
                    continue;
                default:
                    if (ConsoleHelper.getInstance().askYesNoQuestion(SAVE_TEXT_TO_FILE_QUESTION)) {
                        System.out.print(REQUEST_FILE_PATH_TEXT);
                        String path = ConsoleHelper.getInstance().readNotEmptyLine();
                        System.out.print(REQUEST_FILE_NAME_TEXT);
                        String fileName = ConsoleHelper.getInstance().readNotEmptyLine();
                        FileHelper.getInstance().createFile(path, fileName, commandText);
                    }
            }
        }
    }

    private static void startHelpCommand(String[] words) {
        switch (words.length) {
            case 1:
                FileHelper.getInstance().showCommandHelp();
                return;
            case 2:
                FileHelper.getInstance().showCommandHelp(words[1].toUpperCase());
                return;
            default:
                System.out.format(WRONG_NUMBER_OF_ARGS_ERROR_MSG, Command.HELP);
                FileHelper.getInstance().showCommandHelp(Command.HELP);
        }
    }

    private static boolean checkNumberOfArguments(Command command,
                                                  int correctNumber, String[] words) {
        if (words.length - 1 != correctNumber) {
            System.out.format(WRONG_NUMBER_OF_ARGS_ERROR_MSG, command);
            FileHelper.getInstance().showCommandHelp(command);
            return false;
        }
        return true;
    }
}

