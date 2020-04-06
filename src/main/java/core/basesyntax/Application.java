package core.basesyntax;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

import static core.basesyntax.GeneratMenu.generatMenu;

/**
 * Feel free to remove this class and create your own.
 */
public class Application {
    public static void main(String[] args) throws IOException {
        GeneratMenu generatMenu = new GeneratMenu();
        Map<Integer, String> map = generatMenu();
        Scanner scan = new Scanner(System.in);
        boolean isExitChosen = false;
        while (!isExitChosen) {
            String chosenItem = " ";
            System.out.println("Hi, make your choice");
            for (Map.Entry<Integer, String> entry : map.entrySet()) {
                System.out.println(entry.getKey() + entry.getValue());
            }
            //readerList();
            chosenItem = scan.nextLine();
            isExitChosen = MenuController.processUserSelection(chosenItem);

        }

    }
}
