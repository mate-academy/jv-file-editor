package core.basesyntax;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleCommunicator {

    private static final BufferedReader READER
            = new BufferedReader(new InputStreamReader(System.in));

    public void writeMessage(String message) {
        System.out.println(message);
    }

    public String readString() {
        String entry = null;
        while (entry == null) {
            try {
                entry = READER.readLine();
            } catch (IOException e) {
                writeMessage("Repeat your entry:");
            }
        }
        return entry;
    }

    public boolean confirmAction(String message) {
        writeMessage("You are going to:");
        writeMessage(message);
        writeMessage("");
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
}
