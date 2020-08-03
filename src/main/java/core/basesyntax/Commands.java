package core.basesyntax;

import java.io.File;
import java.util.Scanner;

public class Commands implements Utilities {

    private String command;

    private Scanner scanner;

    public void appStart() {
        greeting();
        scanner = new Scanner(System.in);
        appListening();
    }

    public void appListening() {
        command = scanner.nextLine();
        try {
            commandManager();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Команда требует иное количество аргументов");
            appListening();
        } catch (RuntimeException e) {
            System.out.println("Веденная информация неверна!");
        }
    }

    public void commandManager() {
        if (!command.contains("/")) {
            appendTextToFile(command);
            appListening();
        } else {
            String[] commandData = command.split(" ");
            switch (commandData[0]) {
                case "/create":
                    create(commandData[1], commandData[2]);
                    appListening();
                    break;
                case "/read":
                    read(commandData[1], commandData[2]);
                    appListening();
                    break;
                case "/info":
                    information(commandData[1], commandData[2]);
                    appListening();
                    break;
                case "/help":
                    if (commandData.length == 2) {
                        commandsHelp(commandData[1]);
                    } else {
                        help();
                    }
                    appListening();
                    break;
                case "/exit":
                    System.exit(0);
                    break;
                case "/ДА":
                    System.out.println("Файл создан");
                    appListening();
                    break;
                case "/НЕТ":
                    System.out.println("Ну нет, так нет ¯)_(ツ)_(¯");
                    File file = new File("text.txt");
                    file.delete();
                    appListening();
                    break;
                default:
                    break;
            }
        }
    }
}
