package core.basesyntax.command;

import core.basesyntax.FileManager;
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
            FileManager fileManager = new FileManager(HELP);
            COMMUNICATOR.printList(fileManager.readFile());
            return;
        }
        try {
            Operation operation = Operation.valueOf(argument.toUpperCase());
            switch (operation) {
                case CREATE:
                    FileManager fileManager = new FileManager(HELP_CREATE);
                    COMMUNICATOR.printList(fileManager.readFile());
                    break;
                case INFO:
                    fileManager = new FileManager(HELP_INFO);
                    COMMUNICATOR.printList(fileManager.readFile());
                    break;
                case READ:
                    fileManager = new FileManager(HELP_READ);
                    COMMUNICATOR.printList(fileManager.readFile());
                    break;
                default:
                    break;
            }
        } catch (IllegalArgumentException e) {
            COMMUNICATOR.writeMessage("No such command exist. "
                                      + "Print [help] to see available commands.");
        }
    }
}
