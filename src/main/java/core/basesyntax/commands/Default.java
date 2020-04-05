package core.basesyntax.commands;

import java.util.Scanner;

class Default extends Command {

    static final Default INSTANCE = new Default(
            "default",
            "",
            "");

    public Default(String name, String argDescription, String description) {
        super(name, argDescription, description);
    }

    @Override
    public void execute(String... args) {

        if (!args[0].equals("")) {
            System.out.println("Хотите записать текст? (yes/no)");
            String answerWannaWriteInput
                    = new Scanner(System.in).nextLine().toLowerCase();
            if (answerWannaWriteInput.equals("yes") || answerWannaWriteInput.equals("y")) {
                System.out.println("Введите путь и имя файла [path] [filename]: ");
                String answerPathAndFilename =
                        new Scanner(System.in).nextLine().toLowerCase();
                String[] splitData = answerPathAndFilename.split(" ");
                if (splitData.length == 2) {
                    String newCommand =
                            "create "
                                    + splitData[0] + " "
                                    + splitData[1] + " "
                                    + args[0];
                    Create.INSTANCE.execute(newCommand.split(" "));
                } else {
                    System.out.println("Неверный аргумент(ы). "
                            + "Введите \"help\" для справки.");
                }
            }
        }
    }
}
