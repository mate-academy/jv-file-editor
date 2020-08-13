package core.basesyntax.command;

import core.basesyntax.DriveFileService;
import core.basesyntax.FileService;
import core.basesyntax.Operation;
import java.io.IOException;
import java.nio.file.Path;

public class HelpCommand implements Command {
    private static final Path HELP = Path.of("src/main/resources/help/help.txt");
    private static final Path HELP_CREATE = Path.of("src/main/resources/help/helpCreate.txt");
    private static final Path HELP_INFO = Path.of("src/main/resources/help/helpInfo.txt");
    private static final Path HELP_READ = Path.of("src/main/resources/help/helpRead.txt");

    @Override
    public void execute(String argument) throws IOException {
        if (argument.isEmpty()) {
            FileService fileService = new DriveFileService(HELP);
            communicator.printList(fileService.readFile());
            return;
        }
        try {
            Operation operation = Operation.valueOf(argument.toUpperCase());
            switch (operation) {
                case CREATE:
                    FileService fileService = new DriveFileService(HELP_CREATE);
                    communicator.printList(fileService.readFile());
                    break;
                case INFO:
                    fileService = new DriveFileService(HELP_INFO);
                    communicator.printList(fileService.readFile());
                    break;
                case READ:
                    fileService = new DriveFileService(HELP_READ);
                    communicator.printList(fileService.readFile());
                    break;
                default:
                    break;
            }
        } catch (IllegalArgumentException e) {
            communicator.writeMessage("No such command exist. "
                                      + "Print [help] to see available commands.");
        }
    }
}
