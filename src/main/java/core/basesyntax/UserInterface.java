package core.basesyntax;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class UserInterface {
    private Scanner scanner;

    public UserInterface() {
        scanner = new Scanner(System.in);
    }

    public void userInput() throws IOException {
        String inputStr;
        inputStr = scanner.nextLine();
        List<String> splitInputStr = new ArrayList<>(Arrays.asList(inputStr.split(" ")));
        FileEditor fileEditor = new FileEditor();
        while (true) {
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
                    HashMap<String, String> help = new HashMap<>();
                    help.put("create", "\nCommand \"create FILENAME PATH/\""
                            + " - to create new file");
                    help.put("read", "\nCommand \"read FILENAME PATH/\""
                            + " - to read file content");
                    help.put("info", "\nCommand \"info FILENAME PATH/\""
                            + " - to get info about file");
                    help.put("help", "\nCommand \"help COMMAND\""
                            + " - to get info about command");
                    if (splitInputStr.size() == 1) {
                        System.out.println(help.values());
                    } else {
                        System.out.println(help.get(splitInputStr.get(1)) == null
                                ? "Command does not exist" : help.get(splitInputStr.get(1)));
                    }
                    break;
                }
                case "exit": {
                    System.out.println("Good bye!!!");
                    System.exit(0);
                }
                break;
                default: {
                    saveInputText(splitInputStr, fileEditor);
                }
            }
            String controlStr;
            if ((controlStr = scanner.nextLine()) != null) {
                splitInputStr = Arrays.asList(controlStr.split(" "));
            }
        }
    }

    public void saveInputText(List<String> splitInputStr, FileEditor fileEditor)
            throws IOException {
        System.out.println("Do you want to save this text? [yes/no]");
        String key = scanner.nextLine();
        if (key.equalsIgnoreCase("yes")) {
            final String text = splitInputStr.toString();
            System.out.println("Input FILENAME and PATH/");
            splitInputStr = Arrays.asList(scanner.nextLine().split(" "));
            fileEditor.createFile(splitInputStr.get(0), splitInputStr.size() == 2
                    ? splitInputStr.get(1) : " ");
            fileEditor.saveText(splitInputStr.get(0), splitInputStr.size() == 2
                    ? splitInputStr.get(1) : " ", text);
        }
    }
}
