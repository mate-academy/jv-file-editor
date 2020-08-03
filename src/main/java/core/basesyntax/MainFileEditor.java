package core.basesyntax;

import core.basesyntax.exception.DirectoryNotExistException;
import core.basesyntax.exception.FileNotExistException;
import java.io.IOException;

public class MainFileEditor {

    private static final ConsoleCommunicator COMMUNICATOR
            = ConsoleCommunicator.getInstance();

    private static String commandArgument;

    public static void main(String[] args) {
        COMMUNICATOR.writeMessage(" ===== Welcome to File Editor! =====");
        COMMUNICATOR.writeMessage("Enter [command] or \"help\" to see options:");
        Operation operation;
        do {
            operation = askOperation();
            try {
                CommandExecutor.execute(operation, commandArgument);
            } catch (FileNotExistException e) {
                COMMUNICATOR.writeMessage("Specified file is not exist");
            } catch (DirectoryNotExistException e) {
                COMMUNICATOR.writeMessage("Specified directory is not exist");
            } catch (IOException e) {
                COMMUNICATOR.writeMessage("Operation can not be executed");
            }
        } while (operation != Operation.EXIT);
    }

    private static Operation askOperation() {
        while (true) {
            String entry = COMMUNICATOR.readString();
            String[] splitEntry = entry.split(" ", 2);
            try {
                commandArgument = splitEntry.length == 2 ? splitEntry[1] : "";
                return Operation.valueOf(splitEntry[0].toUpperCase());
            } catch (IllegalArgumentException e) {
                if (!entry.isEmpty()) {
                    commandArgument = entry;
                    return Operation.SAVE;
                }
            }
        }
    }
}
