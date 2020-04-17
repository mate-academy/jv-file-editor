package core.basesyntax;

import core.basesyntax.helpers.FileHelper;

import java.util.List;

public class MockFileHelper implements FileHelper {
    private List<String> lastParams;
    private Command lastCommand;

    public boolean isCommandInvoked(Command command, List<String> params) {
        return command == lastCommand && lastParams.equals(params);
    }

    @Override
    public void createFile(String path, String fileName, String fileContent) {
        lastCommand = Command.CREATE;
        lastParams = List.of(path, fileName, fileContent);
    }

    @Override
    public void showFileInfo(String path, String fileName) {
        lastCommand = Command.INFO;
        lastParams = List.of(path, fileName);
    }

    @Override
    public void showFileContent(String path, String fileName) {
        lastCommand = Command.READ;
        lastParams = List.of(path, fileName);
    }

    @Override
    public void showCommandHelp(Command command) {
        lastCommand = Command.HELP;
        lastParams = List.of(command.toString());
    }

    @Override
    public void showCommandHelp() {
        lastCommand = Command.HELP;
        lastParams = List.of();
    }
}
