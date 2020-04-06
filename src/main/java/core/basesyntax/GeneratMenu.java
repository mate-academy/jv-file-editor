package core.basesyntax;

import java.util.HashMap;
import java.util.Map;

public class GeneratMenu {
    public static Map<Integer, String> generatMenu() {
        Map<Integer, String> mapMenu = new HashMap<>();
        mapMenu.put(1, " --Create");
        mapMenu.put(2, " --Read");
        mapMenu.put(3, " --Info");
        mapMenu.put(4, " --HelpCommand");
        mapMenu.put(5, " --Exit");
        return mapMenu;
    }
}
