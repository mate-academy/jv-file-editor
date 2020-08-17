package core.basesyntax;

public class Exiting implements Action {

    @Override
    public void doAction(String command) {
        System.exit(0);
    }
}
