package core.basesyntax;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.print("$ ");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (scanner.hasNextLine()) {
                String inputLine = scanner.nextLine();
                String[] inputSplitData = inputLine.split(" ");
                String command = inputSplitData[0];
                switch (command) {
                    case "create": {
                        Logic.create.execute(inputSplitData);
                        break;
                    }
                    case "read": {
                        Logic.read.execute(inputSplitData);
                        break;
                    }
                    case "info": {
                        Logic.info.execute(inputSplitData);
                        break;
                    }
                    case "help": {
                        Logic.help.execute(inputSplitData);
                        break;
                    }
                    case "exit": {
                        Logic.exit.execute(inputSplitData);
                        break;
                    }
                    default: {
                        if (!command.equals("")) {
                            System.out.println("Хотите записать текст? (yes/no)");
                            String answerWannaWriteInput
                                    = new Scanner(System.in).nextLine().toLowerCase();
                            if (answerWannaWriteInput.equals("yes")) {
                                System.out.println("Введите путь и имя файла [path] [filename]: ");
                                String answerPathAndFilename =
                                        new Scanner(System.in).nextLine().toLowerCase();
                                String[] splitData = answerPathAndFilename.split(" ");
                                if (splitData.length == 2) {
                                    String newCommand =
                                            "create " + splitData[0] + " "
                                                    + splitData[1] + " " + inputLine;
                                    Logic.create.execute(newCommand.split(" "));
                                } else {
                                    System.out.println("Неверный аргумент(ы). "
                                            + "Введите \"help\" для справки.");
                                }
                            }
                        }
                    }
                }
                System.out.print("$ ");
            }
        }
    }
}
