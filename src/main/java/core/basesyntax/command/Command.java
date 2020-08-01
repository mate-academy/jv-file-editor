package core.basesyntax.command;

import core.basesyntax.ConsoleCommunicator;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public interface Command {

    ConsoleCommunicator COMMUNICATOR = ConsoleCommunicator.getInstance();

    void execute(String argument) throws IOException;

    default Path getPath(String argument) {
        String[] splitArgument = argument.split(" ");
        if (splitArgument.length != 2) {
            throw new IllegalArgumentException();
        }
        if (!splitArgument[0].endsWith(File.separator)) {
            splitArgument[0] += File.separator;
        }
        return Path.of(splitArgument[0] + splitArgument[1]);
    }
}
