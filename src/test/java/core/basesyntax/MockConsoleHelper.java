package core.basesyntax;

import core.basesyntax.helpers.ConsoleHelper;

import java.util.Queue;

public class MockConsoleHelper implements ConsoleHelper {
    private final Queue<String> queue;

    public MockConsoleHelper(Queue<String> linesQueue) {
        queue = linesQueue;
    }

    @Override
    public String readNotEmptyLine() {
        if (queue.isEmpty()) {
            throw new RuntimeException("Too many read operations!");
        } else {
            return queue.poll();
        }
    }

    @Override
    public boolean askYesNoQuestion(String question) {
        return readNotEmptyLine().equals("y");
    }
}
