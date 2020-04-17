package core.basesyntax.helpers;

public class RealHelperFactory implements HelperFactory {
    @Override
    public FileHelper getFileHelper() {
        return RealFileHelper.getInstance();
    }

    @Override
    public ConsoleHelper getConsoleHelper() {
        return RealConsoleHelper.getInstance();
    }
}
