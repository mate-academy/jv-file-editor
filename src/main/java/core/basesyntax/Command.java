package core.basesyntax;

public enum Command {
    CREATE(new String[]{"[path] [filename] - Create file"}),
    READ(new String[]{"[path] [filename] - Read file"}),
    INFO(new String[]{"[path] [filename] - Read file info"}),
    HELP(new String[]{"Show  explanation for all commands",
            "[command]  - Show explanation for command"}),
    EXIT(new String[]{"Exit the program with status 0"});
    private String[] help;

    Command(String[] help) {
        this.help = help;
    }

    public String[] getHelp() {
        return help;
    }

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
