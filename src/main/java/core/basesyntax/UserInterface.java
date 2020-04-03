package core.basesyntax;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class UserInterface {
    Scanner scanner = new Scanner(System.in);

    public void userInput() {
        String inputStr;
        inputStr = scanner.nextLine();
        List<String> splitInputStr = new ArrayList<>(Arrays.asList(inputStr.split(" ")));
        FileEditor fileEditor = new FileEditor();
        while (!splitInputStr.get(0).equals("exit")) {
            switch (splitInputStr.get(0)) {
                case "create": {
                    fileEditor.createFile(splitInputStr.get(1), splitInputStr.size() == 3
                            ? splitInputStr.get(2) : " ");
                    break;
                }
                case "read": {
                    fileEditor.readFile(splitInputStr.get(1), splitInputStr.size() == 3
                            ? splitInputStr.get(2) : " ");
                    break;
                }
                case "info": {
                    fileEditor.getInfo(splitInputStr.get(1), splitInputStr.size() == 3
                            ? splitInputStr.get(2) : " ");
                    break;
                }
                case "help": {
                    if (splitInputStr.size() == 1) {
                        getHelp();
                    } else {
                        getCommandHelp(splitInputStr.get(1));
                    }
                    break;
                }
                default: {
                    System.out.println("Do you want to save this text?");
                    String key = scanner.nextLine();
                    if (key.equalsIgnoreCase("yes")) {
                        final String text = splitInputStr.toString();
                        //String tempText = text;
                        System.out.println("Input FILENAME and PATH/");
                        splitInputStr = Arrays.asList(scanner.nextLine().split(" "));
                        fileEditor.createFile(splitInputStr.get(0), splitInputStr.size() == 2
                                ? splitInputStr.get(1) : " ");
                        fileEditor.saveText(splitInputStr.get(0), splitInputStr.size() == 2
                                ? splitInputStr.get(1) : " ", text);
                    }
                }
            }
            String controlStr;
            if ((controlStr = scanner.nextLine()) != null) {
                splitInputStr = Arrays.asList(controlStr.split(" "));
            }
        }
    }

    public void getCommandHelp(String command) {
        switch (command) {
            case "create":
                System.out.println("Command \"create FILENAME PATH/\""
                        + " - to create new file");
                break;
            case "read":
                System.out.println("Command \"read FILENAME PATH/\""
                        + " - to read file content");
                break;
            case "info":
                System.out.println("Command \"info FILENAME PATH/\""
                        + " - to get info about file");
                break;
            case "help":
                System.out.println("Command \"help COMMAND\""
                        + " - to get info about command");
                break;
            default:
                System.out.println("Incorrect command. Call \"help\""
                        + " - to view list of commands.");
        }
    }

    public void getHelp() {
        System.out.println("Command \"create FILENAME PATH/\""
                + " - to create new file");
        System.out.println("Command \"read FILENAME PATH/\" -"
                + " to read file content");
        System.out.println("Command \"info FILENAME PATH/\" -"
                + " to get info about file");
        System.out.println("Command \"help \" -"
                + " to get info about commands");
        System.out.println("Command \"help COMMAND\" -"
                + " to get info about command");
    }
}
