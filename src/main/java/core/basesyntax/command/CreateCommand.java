package core.basesyntax.command;

import core.basesyntax.DriveFileService;
import core.basesyntax.FileService;
import java.io.IOException;

public class CreateCommand implements Command {
    @Override
    public void execute(String argument) throws IOException {
        FileService fileService = new DriveFileService(argument);
        if (isContinueExecutingIfFileExist(fileService.getFile())) {
            fileService.createFile();
            communicator.writeMessage("File has been created successfully.");
        }
    }
}
