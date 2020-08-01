package core.basesyntax;

import java.io.IOException;

public class MainFileEditor {

    private static final ConsoleCommunicator COMMUNICATOR
            = ConsoleCommunicator.getInstance();

    private static String commandArgument;

    public static void main(String[] args) {
        Operation operation;
        do {
            operation = askOperation();
            try {
                CommandExecutor.execute(operation, commandArgument);
            } catch (IOException | IllegalArgumentException e) {
                COMMUNICATOR.writeMessage("File is not exist");
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
