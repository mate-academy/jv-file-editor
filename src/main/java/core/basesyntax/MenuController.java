package core.basesyntax;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MenuController {

    public Map<Integer, String> generatMenu() {
        Map<Integer, String> mapMenu = new HashMap<>();
        mapMenu.put(1, " --Create file");
        mapMenu.put(2, " --Read file");
        mapMenu.put(3, " --Info about file");
        mapMenu.put(4, " --HelpCommand");
        mapMenu.put(5, " --Exit");
        return mapMenu;
    }

    public boolean processUserSelection(String chosenItem) throws IOException {
        FileEditor fileEditor = new FileEditor();
        boolean isExitChosen = false;
        switch (chosenItem) {
            case "1":
                System.out.println("You chose: Create new files");
                fileEditor.createFile();
                break;
            case "2":
                System.out.println("You chose: Read file");
                fileEditor.readFiles();
                break;
            case "3":
                System.out.println("You chose: Info about file");
                fileEditor.getInfoAboutFile();
                break;
            case "4":
                System.out.println("You chose: helpCommand");
                break;
            case "5":
                System.out.println("You chose: Exit ");
                isExitChosen = true;
                break;

            default:
                System.out.println("Error");
                System.out.println("Save these changes?");

        }
        return isExitChosen;
    }
}
