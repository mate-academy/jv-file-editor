package core.basesyntax.command;

import core.basesyntax.ConsoleCommunicator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public interface Command {

    ConsoleCommunicator communicator = ConsoleCommunicator.getInstance();

    void execute(String argument) throws IOException;

    default boolean isContinueExecutingIfFileExist(Path file) {
        if (Files.isRegularFile(file)) {
            communicator.writeMessage("File already exist. All data will be deleted. Continue?");
            return communicator.confirmAction();
        }
        return true;
    }
}
