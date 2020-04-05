package core.basesyntax;

import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        FileEditor fileEditor = new FileEditor();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please, enter the command");
        while (scanner.hasNextLine()) {
            String[] userInput = scanner.nextLine().split("[ ]+");
            switch (userInput[0]) {
                case "read":
                    fileEditor.readFile(userInput[1], userInput[2]);
                    break;
                case "create":
                    fileEditor.createFile(userInput[1], userInput[2]);
                    break;
                case "info":
                    fileEditor.getInfo(userInput[1], userInput[2]);
                    break;
                case "exit":
                    fileEditor.exitEditor();
                    break;
                case "help":
                    if (userInput.length > 1) {
                        fileEditor.getHelp(userInput[1]);
                    }
                    fileEditor.getHelp();
                    break;
                default:
                    System.out.println("Do you want to save your text?");
                    switch (scanner.nextLine()) {
                        case "yes":
                            System.out.println("Enter the path and the filename");
                            String[] input = scanner.nextLine().split("[ ]+");
                            fileEditor.createFile(input[0], input[1]);
                            fileEditor.writeFile(input[0], input[1], userInput);
                            break;
                        case "no":
                            fileEditor.exitEditor();
                            break;
                        default:
                            System.out.println("Please, enter yes/no");
                            break;
                    }
                    break;
            }
        }
    }
}
