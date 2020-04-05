package core.basesyntax;

import core.basesyntax.commands.Command;

import java.util.Scanner;

public class Console {
    public static void handle(Scanner scanner){
        while (true) {
            if (scanner.hasNextLine()) {
                String inputLine = scanner.nextLine();
                String[] inputSplitData = inputLine.split(" ");
                String command = inputSplitData[0];
                switch (command) {
                    case "create": {
                        Command.create(inputSplitData);
                        break;
                    }
                    case "read": {
                        Command.read(inputSplitData);
                        break;
                    }
                    case "info": {
                        Command.info(inputSplitData);
                        break;
                    }
                    case "help": {
                        Command.help(inputSplitData);
                        break;
                    }
                    case "exit": {
                        Command.exit(inputSplitData);
                        break;
                    }
                    default: {
                        Command.executeUnrecognizedInputCase(inputLine);
                        break;
                    }
                }
                System.out.print("$ ");
            }
        }
    }
}
