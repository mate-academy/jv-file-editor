package core.basesyntax;

public enum Command {
    CREATE("[path] [filename] - Create file"),
    READ("[path] [filename] - Read file"),
    INFO("[path] [filename] - Read file info"),
    HELP("Show  explanation for all commands\n"
            + "[command]  - Show explanation for command"),
    EXIT("Exit the program with status 0");
    private String help;

    Command(String help) {
        this.help = help;
    }

    public String getHelp() {
        return help;
    }

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
