package core.basesyntax.command;

import core.basesyntax.DriveFileService;
import core.basesyntax.FileService;
import java.io.IOException;

public class SaveCommand implements Command {
    @Override
    public void execute(String argument) throws IOException {
        communicator.writeMessage("Would you like to save text above?");
        if (!communicator.confirmAction()) {
            return;
        }
        communicator.writeMessage("Enter [path] [file-name] for file creation:");
        String entry = communicator.readString();
        FileService fileService = new DriveFileService(entry);
        if (isContinueExecutingIfFileExist(fileService.getFile())) {
            fileService.saveFile(argument);
            communicator.writeMessage("File has been created successfully!");
        }
    }
}
