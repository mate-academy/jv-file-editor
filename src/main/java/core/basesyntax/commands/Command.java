package core.basesyntax.commands;

public abstract class Command {

    private String name;
    private String description;
    private String argDescription;

    public Command(String name, String argDescription, String description) {
        this.name = name;
        this.argDescription = argDescription;
        this.description = description;
    }

    abstract void execute(String... args);

    public String toString() {
        return String.format("%7s   %-25s   %-20s", name, argDescription, description);
    }

    public static void create(String... args) {
        Create.INSTANCE.execute(args);
    }

    public static void read(String... args) {
        Read.INSTANCE.execute(args);
    }

    public static void info(String... args) {
        Info.INSTANCE.execute(args);
    }

    public static void help(String... args) {
        Help.INSTANCE.execute(args);
    }

    public static void exit(String... args) {
        Exit.INSTANCE.execute(args);
    }

    public static void executeUnrecognizedInputCase(String inputString) {
        Default.INSTANCE.execute(inputString);
    }

}
