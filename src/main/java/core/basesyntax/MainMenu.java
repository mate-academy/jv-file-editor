package core.basesyntax;

import static core.basesyntax.Command.CREATE;
import static core.basesyntax.Command.EXIT;
import static core.basesyntax.Command.HELP;
import static core.basesyntax.Command.INFO;
import static core.basesyntax.Command.READ;
import static core.basesyntax.Command.UNKNOWN;

import core.basesyntax.helpers.HelperFactory;

public class MainMenu {
    private static final String EMPTY_STRING = "";
    private static final String WRONG_NUMBER_OF_ARGS_ERROR_MSG =
            "Wrong number of arguments for %s command.\n";
    private static final String REG_EXP_MULTI_WHITESPACE = "[\\s]+";

    private final HelperFactory factory;

    public MainMenu(HelperFactory helperFactory) {
        factory = helperFactory;
    }

    public void start() {
        for (; ; ) {
            String commandText = factory.getConsoleHelper().readNotEmptyLine();
            String[] words = commandText.trim().split(REG_EXP_MULTI_WHITESPACE);
            switch (strToCommand(words[0])) {
                case CREATE:
                    if (checkNumberOfArguments(CREATE, 2, words)) {
                        factory.getFileHelper().createFile(words[1], words[2], EMPTY_STRING);
                    }
                    continue;
                case READ:
                    if (checkNumberOfArguments(READ, 2, words)) {
                        factory.getFileHelper().showFileContent(words[1], words[2]);
                    }
                    continue;
                case INFO:
                    if (checkNumberOfArguments(INFO, 2, words)) {
                        factory.getFileHelper().showFileInfo(words[1], words[2]);
                    }
                    continue;
                case HELP:
                    startHelpCommand(words);
                    continue;
                case EXIT:
                    if (checkNumberOfArguments(EXIT, 0, words)) {
                        return;
                    }
                    continue;
                default:
                    if (factory.getConsoleHelper().askYesNoQuestion(
                            "Do you want to save this text to file?")) {
                        System.out.print("Input file path: ");
                        String path = factory.getConsoleHelper().readNotEmptyLine();
                        System.out.print("Input file name: ");
                        String fileName = factory.getConsoleHelper().readNotEmptyLine();
                        factory.getFileHelper().createFile(path, fileName, commandText);
                    }
            }
        }
    }

    private void startHelpCommand(String[] words) {
        switch (words.length) {
            case 1:
                factory.getFileHelper().showCommandHelp();
                return;
            case 2:
                factory.getFileHelper().showCommandHelp(strToCommand(words[1].toUpperCase()));
                return;
            default:
                System.out.format(WRONG_NUMBER_OF_ARGS_ERROR_MSG, HELP);
                factory.getFileHelper().showCommandHelp(HELP);
        }
    }

    private boolean checkNumberOfArguments(Command command,
                                                  int correctNumber, String[] words) {
        if (words.length - 1 != correctNumber) {
            System.out.format(WRONG_NUMBER_OF_ARGS_ERROR_MSG, command);
            factory.getFileHelper().showCommandHelp(command);
            return false;
        }
        return true;
    }

    private Command strToCommand(String str) {
        str = str.toUpperCase();
        for (Command item : Command.values()) {
            if (item.toString().equals(str)) {
                return item;
            }
        }
        return UNKNOWN;
    }
}
