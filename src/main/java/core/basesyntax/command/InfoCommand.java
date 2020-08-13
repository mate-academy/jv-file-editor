package core.basesyntax.command;

import core.basesyntax.DriveFileService;
import core.basesyntax.FileService;
import java.io.IOException;

public class InfoCommand implements Command {
    @Override
    public void execute(String argument) throws IOException {
        FileService fileService = new DriveFileService(argument);
        communicator.writeMessage(fileService.getFileInfo());
    }
}
