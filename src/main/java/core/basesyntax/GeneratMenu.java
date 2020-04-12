package core.basesyntax;

import java.util.HashMap;
import java.util.Map;

public class GeneratMenu {
    public static Map<Integer, String> generatMenu() {
        Map<Integer, String> mapMenu = new HashMap<>();
        mapMenu.put(1, " --Create file");
        mapMenu.put(2, " --Read file");
        mapMenu.put(3, " --Info about file");
        mapMenu.put(4, " --HelpCommand");
        mapMenu.put(5, " --Exit");
        return mapMenu;
    }
}
