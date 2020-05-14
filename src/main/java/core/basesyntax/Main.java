package core.basesyntax;

import core.basesyntax.controller.ConsoleHandler;

public class Main {

    public static void main(String[] args) {
        System.out.print("Welcome to jv-file-editor. Type \"/help\" for help.\n$ ");
        ConsoleHandler console = new ConsoleHandler();
        console.handle();
    }
}
