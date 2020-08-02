package core.basesyntax.command;

import core.basesyntax.FileManager;
import core.basesyntax.exception.DirectoryNotExistException;
import java.io.IOException;
import java.nio.file.Path;

public class CreateCommand implements Command {
    @Override
    public void execute(String argument) throws DirectoryNotExistException, IOException {
        Path fileToCreate = getPath(argument);
        if (isContinueExecutingIfFileExist(fileToCreate)) {
            FileManager fileManager = new FileManager(fileToCreate);
            fileManager.createFile();
            COMMUNICATOR.writeMessage("File has been created successfully.");
        }
    }
}
