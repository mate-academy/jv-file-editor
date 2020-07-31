package core.basesyntax;

import java.util.Arrays;

public class MainFileEditor {

    public static final ConsoleCommunicator COMMUNICATOR
            = new ConsoleCommunicator();

    private static String commandArgument;

    public static void main(String[] args) {
        while(true) {
            Operation operation = askOperation();
        }
    }

    private static Operation askOperation() {
        String entry;
        do {
            entry = COMMUNICATOR.readString();
            String[] splitEntry = entry.split(" ", 2);
            try {
                commandArgument = splitEntry[1];
                return Operation.valueOf(splitEntry[0].toUpperCase());
            } catch (IllegalArgumentException e) {
                commandArgument = "";
            }

        } while (entry.isEmpty());
        return null;
    }
}
