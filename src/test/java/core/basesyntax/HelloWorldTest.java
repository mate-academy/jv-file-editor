package core.basesyntax;

import org.junit.Test;

/**
 * Feel free to remove this class and create your own.
 */
public class HelloWorldTest {

    @Test
    public void startSimpleFileEditor() {
        SimpleFileEditor fileEditor = new SimpleFileEditor();
        fileEditor.readingCommand("stop");
    }
}
