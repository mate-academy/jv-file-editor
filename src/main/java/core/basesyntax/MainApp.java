package core.basesyntax;

import core.basesyntax.helpers.RealHelperFactory;

public class MainApp {
    public static void main(String[] args) {
        new MainMenu(new RealHelperFactory()).start();
    }
}

