package core.basesyntax;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) throws IOException {
        MenuController menuController = new MenuController();
        Map<Integer, String> map = menuController.generatMenu();
        Scanner scan = new Scanner(System.in);
        boolean isExitChosen = false;
        while (!isExitChosen) {
            String chosenItem = " ";
            System.out.println("Make your choice");
            for (Map.Entry<Integer, String> entry : map.entrySet()) {
                System.out.println(entry.getKey() + entry.getValue());
            }
            chosenItem = scan.nextLine();
            isExitChosen = menuController.processUserSelection(chosenItem);

        }

    }
}
