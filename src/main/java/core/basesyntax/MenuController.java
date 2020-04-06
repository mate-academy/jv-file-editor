package core.basesyntax;


import java.io.IOException;
import java.util.Scanner;

public class MenuController {
    public static boolean processUserSelection(String chosenItem) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hi, make your choice");
        boolean isExitChosen = false;
        switch (chosenItem) {
            case "1":
                System.out.println("You chose: Create new files");
                break;
            case "2":
                System.out.println("You chose: Read file");
                //ReadFile readFile = new ReadFile();
                ReadFile.readFiles();
                break;
            case "3":
                System.out.println("You chose: Info about file");
                break;
            case"4":
                System.out.println("You chose: helpCommand");
                break;
            case "5":
                System.out.println("You chose: Exit ");
           break;
            default:
                System.out.println("Error");
                break;
        }
return isExitChosen;
    }
}
