package core.basesyntax.command;

import core.basesyntax.DriveFileService;
import core.basesyntax.FileService;
import java.io.IOException;
import java.nio.file.Path;

public class CreateCommand implements Command {
    @Override
    public void execute(String argument) throws IOException {
        Path fileToCreate = getPath(argument);
        if (isContinueExecutingIfFileExist(fileToCreate)) {
            FileService fileService = new DriveFileService(fileToCreate);
            fileService.createFile();
            COMMUNICATOR.writeMessage("File has been created successfully.");
        }
    }
}
