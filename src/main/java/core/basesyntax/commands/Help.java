package core.basesyntax.commands;

class Help extends Command {

    static final Help INSTANCE = new Help(
            "help",
            "[command]",
            "Выводит в консоль "
                    + "все доступные комманды и информацию к ним.");

    public Help(String name, String argDescription, String description) {
        super(name, argDescription, description);
    }

    @Override
    public void execute(String... args) {
        if (args.length == 2) {
            switch (args[1]) {
                case "exit": {
                    System.out.println(Exit.INSTANCE.toString());
                    break;
                }
                case "create": {
                    System.out.println(Create.INSTANCE.toString());
                    break;
                }
                case "read": {
                    System.out.println(Read.INSTANCE.toString());
                    break;
                }
                case "info": {
                    System.out.println(Info.INSTANCE.toString());
                    break;
                }
                case "help": {
                    System.out.println(Help.INSTANCE.toString());
                    break;
                }
                default: {
                    System.out.println("Неверный аргумент : "
                            + args[1] + ". \"help\" для справки.");
                    break;
                }
            }
        }
        if (args.length == 1) {
            System.out.println(Create.INSTANCE.toString());
            System.out.println(Read.INSTANCE.toString());
            System.out.println(Info.INSTANCE.toString());
            System.out.println(Help.INSTANCE.toString());
            System.out.println(Exit.INSTANCE.toString());
        }
    }
}
