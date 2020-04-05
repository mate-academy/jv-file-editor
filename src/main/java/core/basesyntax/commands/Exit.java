package core.basesyntax.commands;

public class Exit extends Command {

    static final Exit INSTANCE = new Exit(
            "exit",
            "[code]",
            "Завершение работы программы.");

    public Exit(String name, String argDescription, String description) {
        super(name, argDescription, description);
    }

    @Override
    public void execute(String... args) {
        int code = 0;
        if (args != null && args.length == 2) {
            try {
                code = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        System.exit(code);
    }
}
