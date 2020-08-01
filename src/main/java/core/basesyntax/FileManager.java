package core.basesyntax;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileManager {

    private Path file;

    public FileManager(Path file) {
        this.file = file;
    }

    public void createFile() throws IOException {
        Files.createFile(file);
    }

    public List<String> readFile() throws IOException {
        return Files.readAllLines(file);
    }
}
