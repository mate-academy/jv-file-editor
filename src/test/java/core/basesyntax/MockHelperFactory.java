package core.basesyntax;

import core.basesyntax.helpers.ConsoleHelper;
import core.basesyntax.helpers.FileHelper;
import core.basesyntax.helpers.HelperFactory;

public class MockHelperFactory implements HelperFactory {
    private ConsoleHelper consoleHelper;
    private FileHelper fileHelper;

    public MockHelperFactory(ConsoleHelper consoleHelper, FileHelper fileHelper) {
        this.consoleHelper = consoleHelper;
        this.fileHelper = fileHelper;
    }

    @Override
    public FileHelper getFileHelper() {
        return fileHelper;
    }

    @Override
    public ConsoleHelper getConsoleHelper() {
        return consoleHelper;
    }
}
