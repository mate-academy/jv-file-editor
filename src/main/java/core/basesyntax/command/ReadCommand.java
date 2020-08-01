package core.basesyntax.command;

import core.basesyntax.FileManager;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ReadCommand implements Command {
    @Override
    public void execute(String argument) throws IOException {
        FileManager manager = new FileManager(getPath(argument));
        manager.readFile().forEach(COMMUNICATOR::writeMessage);
    }
}
