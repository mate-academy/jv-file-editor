package core.basesyntax.command;

import core.basesyntax.ConsoleCommunicator;
import core.basesyntax.exception.DirectoryNotExistException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

public interface Command {

    ConsoleCommunicator COMMUNICATOR = ConsoleCommunicator.getInstance();

    void execute(String argument) throws IOException, DirectoryNotExistException;

    default Path getPath(String argument) throws DirectoryNotExistException, NoSuchFileException {
        int lastWhitespaceIndex = argument.lastIndexOf(" ");
        if (lastWhitespaceIndex > 0) {
            Path directory = Path.of(argument.substring(0, lastWhitespaceIndex));
            if (!Files.isDirectory(directory)) {
                throw new DirectoryNotExistException();
            }
            return directory.resolve(argument.substring(lastWhitespaceIndex + 1));
        }
        throw new NoSuchFileException("");
    }

    default boolean isContinueExecutingIfFileExist(Path file) {
        if (Files.isRegularFile(file)) {
            COMMUNICATOR.writeMessage("File already exist. All data will be deleted. Continue?");
            return COMMUNICATOR.confirmAction();
        }
        return true;
    }
}
