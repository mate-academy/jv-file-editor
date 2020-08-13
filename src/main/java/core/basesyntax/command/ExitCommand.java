package core.basesyntax.command;

public class ExitCommand implements Command {
    @Override
    public void execute(String argument) {
        communicator.writeMessage("=== Good bay! ===");
    }
}
