package core.basesyntax;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TextFilesTest {
    private static final String COMMAND_Y = "y";
    private static final String COMMAND_N = "n";
    private static final String COMMAND1 = "create";
    private static final String COMMAND2 = "read";
    private static final String COMMAND3 = "info";
    private static final String COMMAND4 = "help";
    private static final String COMMAND5 = "exit";
    private static final String sourceDirName = "src";
    private static final String mainFileName = "Test.java";

//    @Test
//    public void create() throws IOException {
//        TextFiles textFiles = new TextFiles();
//        textFiles.create(sourceDirName, mainFileName);
//        Assert.assertTrue(Files.exists(Paths.get(sourceDirName + File.separator + mainFileName)));
//    }

    @Test
    public void read() {
        TextFiles textFiles = new TextFiles();
    }

    @Test
    public void info() {

    }

    @Test
    public void helpWithoutParamter() {
    }

    @Test
    public void help() {
    }

    @Test
    public void exit() {
    }
}