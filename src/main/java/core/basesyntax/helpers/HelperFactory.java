package core.basesyntax.helpers;

public interface HelperFactory {
    FileHelper getFileHelper();

    ConsoleHelper getConsoleHelper();
}
