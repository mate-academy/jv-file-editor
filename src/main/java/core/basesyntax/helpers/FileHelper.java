package core.basesyntax.helpers;

import core.basesyntax.Command;

public interface FileHelper {
    void createFile(String path, String fileName, String fileContent);

    void showFileInfo(String path, String fileName);

    void showFileContent(String path, String fileName);

    void showCommandHelp(Command command);

    void showCommandHelp();
}
