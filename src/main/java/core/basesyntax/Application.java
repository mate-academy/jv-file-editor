package core.basesyntax;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

import static core.basesyntax.GeneratMenu.generatMenu;

public class Application {
    public static void main(String[] args) throws IOException {
        Map<Integer, String> map = generatMenu();
        Scanner scan = new Scanner(System.in);
        boolean isExitChosen = false;
        while (!isExitChosen) {
            String chosenItem = " ";
            System.out.println("Make your choice");
            for (Map.Entry<Integer, String> entry : map.entrySet()) {
                System.out.println(entry.getKey() + entry.getValue());
            }
            //readerList();
            chosenItem = scan.nextLine();
            isExitChosen = MenuController.processUserSelection(chosenItem);

        }

    }
}
