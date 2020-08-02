package core.basesyntax.command;

import core.basesyntax.FileManager;
import core.basesyntax.exception.DirectoryNotExistException;
import java.io.IOException;

public class ReadCommand implements Command {
    @Override
    public void execute(String argument) throws IOException, DirectoryNotExistException {
        FileManager manager = new FileManager(getPath(argument));
        COMMUNICATOR.printList(manager.readFile());
    }
}
