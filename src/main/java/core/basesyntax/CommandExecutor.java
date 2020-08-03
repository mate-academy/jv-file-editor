package core.basesyntax;

import core.basesyntax.command.Command;
import core.basesyntax.command.CreateCommand;
import core.basesyntax.command.ExitCommand;
import core.basesyntax.command.HelpCommand;
import core.basesyntax.command.InfoCommand;
import core.basesyntax.command.ReadCommand;
import core.basesyntax.command.SaveCommand;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CommandExecutor {

    private static final Map<Operation, Command> allKnownCommandsMap = new HashMap<>();

    static {
        allKnownCommandsMap.put(Operation.CREATE, new CreateCommand());
        allKnownCommandsMap.put(Operation.READ, new ReadCommand());
        allKnownCommandsMap.put(Operation.INFO, new InfoCommand());
        allKnownCommandsMap.put(Operation.HELP, new HelpCommand());
        allKnownCommandsMap.put(Operation.EXIT, new ExitCommand());
        allKnownCommandsMap.put(Operation.SAVE, new SaveCommand());
    }

    public static void execute(Operation operation, String commandArgument)
            throws IOException {
        allKnownCommandsMap.get(operation).execute(commandArgument);
    }
}
