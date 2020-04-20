package core.basesyntax;

import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Feel free to remove this class and create your own.
 */
public class MainMenuTest {
    private static final String TEST_PATH = "testPath";
    private static final String TEST_FILE_NAME = "testFileName";;

    @Test
    public void mainMenuInfoCommandTest() {
        Queue<String> queue = new LinkedList<>();
        queue.add("info " + TEST_PATH + " " + TEST_FILE_NAME);
        queue.add("exit");

        MockConsoleHelper consoleHelper = new MockConsoleHelper(queue);
        MockFileHelper fileHelper = new MockFileHelper();

        MainMenu menu = new MainMenu(new MockHelperFactory(consoleHelper, fileHelper));

        menu.start();
        Assert.assertTrue("INFO command invoke fail",
                fileHelper.isCommandInvoked(Command.INFO, List.of(TEST_PATH, TEST_FILE_NAME)));
    }

    @Test
    public void mainMenuReadCommandTest() {
        Queue<String> queue = new LinkedList<>();
        queue.add("read " + TEST_PATH + " " + TEST_FILE_NAME);
        queue.add("exit");

        MockConsoleHelper consoleHelper = new MockConsoleHelper(queue);
        MockFileHelper fileHelper = new MockFileHelper();

        MainMenu menu = new MainMenu(new MockHelperFactory(consoleHelper, fileHelper));

        menu.start();
        Assert.assertTrue("READ command invoke fail",
                fileHelper.isCommandInvoked(Command.READ, List.of(TEST_PATH, TEST_FILE_NAME)));
    }

    @Test
    public void mainMenuCreateCommandTest() {
        Queue<String> queue = new LinkedList<>();
        queue.add("create " + TEST_PATH + " " + TEST_FILE_NAME);
        queue.add("exit");

        MockConsoleHelper consoleHelper = new MockConsoleHelper(queue);
        MockFileHelper fileHelper = new MockFileHelper();

        MainMenu menu = new MainMenu(new MockHelperFactory(consoleHelper, fileHelper));

        menu.start();
        Assert.assertTrue("CREATE command invoke fail",
                fileHelper.isCommandInvoked(Command.CREATE, List.of(TEST_PATH, TEST_FILE_NAME, "")));
    }

    @Test
    public void mainMenuSaveTextToFileCommandTest() {
        Queue<String> queue = new LinkedList<>();
        String fileContent = "some text";
        queue.add(fileContent);
        queue.add("y");
        queue.add(TEST_PATH);
        queue.add(TEST_FILE_NAME);
        queue.add("exit");

        MockConsoleHelper consoleHelper = new MockConsoleHelper(queue);
        MockFileHelper fileHelper = new MockFileHelper();

        MainMenu menu = new MainMenu(new MockHelperFactory(consoleHelper, fileHelper));

        menu.start();
        Assert.assertTrue("Save text to file command invoke fail",
                fileHelper.isCommandInvoked(Command.CREATE, List.of(TEST_PATH, TEST_FILE_NAME, fileContent)));
    }

    @Test
    public void mainMenuHelpCommandTest() {
        Queue<String> queue = new LinkedList<>();
        queue.add("help");
        queue.add("exit");

        MockConsoleHelper consoleHelper = new MockConsoleHelper(queue);
        MockFileHelper fileHelper = new MockFileHelper();

        MainMenu menu = new MainMenu(new MockHelperFactory(consoleHelper, fileHelper));

        menu.start();
        Assert.assertTrue("HELP command invoke fail",
                fileHelper.isCommandInvoked(Command.HELP, List.of()));
    }

    @Test
    public void mainMenuHelpCommandWithParamsTest() {
        Queue<String> queue = new LinkedList<>();
        queue.add("help " + Command.CREATE);
        queue.add("exit");

        MockConsoleHelper consoleHelper = new MockConsoleHelper(queue);
        MockFileHelper fileHelper = new MockFileHelper();

        MainMenu menu = new MainMenu(new MockHelperFactory(consoleHelper, fileHelper));

        menu.start();
        Assert.assertTrue("HELP command with params invoke fail",
                fileHelper.isCommandInvoked(Command.HELP, List.of(Command.CREATE.toString())));
    }
}
