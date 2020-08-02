package core.basesyntax.command;

public class ExitCommand implements Command {
    @Override
    public void execute(String argument) {
        COMMUNICATOR.writeMessage("=== Good bay! ===");
    }
}
