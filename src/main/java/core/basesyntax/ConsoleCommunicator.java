package core.basesyntax;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ConsoleCommunicator {

    private static final BufferedReader reader
            = new BufferedReader(new InputStreamReader(System.in));

    private static final ConsoleCommunicator instance = new ConsoleCommunicator();

    private ConsoleCommunicator() {
    }

    public static ConsoleCommunicator getInstance() {
        return instance;
    }

    public void writeMessage(String message) {
        System.out.println(message);
    }

    public String readString() {
        String entry = null;
        while (entry == null) {
            try {
                entry = reader.readLine();
            } catch (IOException e) {
                writeMessage("Repeat your entry:");
            }
        }
        return entry;
    }

    public boolean confirmAction() {
        do {
            writeMessage("Enter \"y\" for \"yes\" or \"n\" for \"no\"");
            String entry = readString();
            if (entry.equalsIgnoreCase("y")) {
                return true;
            }
            if (entry.equalsIgnoreCase("n")) {
                return false;
            }
        } while (true);
    }

    public void printList(List<String> lines) {
        lines.forEach(this::writeMessage);
    }
}
