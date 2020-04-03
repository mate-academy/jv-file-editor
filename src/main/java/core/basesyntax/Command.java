package core.basesyntax;

abstract class Command {

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
}
