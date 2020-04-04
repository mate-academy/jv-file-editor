package core.basesyntax;

import core.basesyntax.commands.Create;
import core.basesyntax.commands.Default;
import core.basesyntax.commands.Exit;
import core.basesyntax.commands.Help;
import core.basesyntax.commands.Info;
import core.basesyntax.commands.Read;
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
                        Create.INSTANCE.execute(inputSplitData);
                        break;
                    }
                    case "read": {
                        Read.INSTANCE.execute(inputSplitData);
                        break;
                    }
                    case "info": {
                        Info.INSTANCE.execute(inputSplitData);
                        break;
                    }
                    case "help": {
                        Help.INSTANCE.execute(inputSplitData);
                        break;
                    }
                    case "exit": {
                        Exit.INSTANCE.execute(inputSplitData);
                        break;
                    }
                    default: {
                        Default.INSTANCE.execute(inputLine);
                    }
                }
                System.out.print("$ ");
            }
        }
    }
}
