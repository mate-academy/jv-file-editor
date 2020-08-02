package core.basesyntax.command;

import core.basesyntax.FileManager;
import core.basesyntax.exception.DirectoryNotExistException;
import java.io.IOException;
import java.nio.file.Path;

public class SaveCommand implements Command {
    @Override
    public void execute(String argument) throws DirectoryNotExistException, IOException {
        COMMUNICATOR.writeMessage("Would you like to save text above?");
        if (!COMMUNICATOR.confirmAction()) {
            return;
        }
        COMMUNICATOR.writeMessage("Enter [path] [file-name] for file creation:");
        String entry = COMMUNICATOR.readString();
        Path fileToSave = getPath(entry);
        if (isContinueExecutingIfFileExist(fileToSave)) {
            FileManager fileManager = new FileManager(fileToSave);
            fileManager.saveFile(argument);
            COMMUNICATOR.writeMessage("File has been created successfully!");
        }
    }
}
