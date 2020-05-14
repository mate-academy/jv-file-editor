package core.basesyntax.controller;

import core.basesyntax.service.ConsoleServiceImpl;
import java.util.Scanner;

public class ConsoleHandler {
    private Scanner scanner;
    private ConsoleServiceImpl service;

    public ConsoleHandler() {
        scanner = new Scanner(System.in);
        service = new ConsoleServiceImpl();
    }

    public void handle() {
        while (true) {
            if (scanner.hasNextLine()) {
                String inputLine = scanner.nextLine();
                String[] inputSplitData = inputLine.split(" ");
                String command = inputSplitData[0].toLowerCase();
                switch (command) {
                    case "create": {
                        service.create(inputSplitData);
                        break;
                    }
                    case "read": {
                        service.read(inputSplitData);
                        break;
                    }
                    case "info": {
                        service.info(inputSplitData);
                        break;
                    }
                    case "help": {
                        service.help(inputSplitData);
                        break;
                    }
                    case "exit": {
                        service.exit();
                        break;
                    }
                    default: {
                        service.execDefaultCommand(inputLine);
                        break;
                    }
                }
                System.out.print("$ ");
            }
        }
    }
}
