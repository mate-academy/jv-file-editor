package core.basesyntax;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileCreator {
    public String create(String path, String filename) {
        Path newFilePath = Paths.get(path + FileSystems.getDefault().getSeparator() + filename);
        if (Files.exists(newFilePath)) {
            throw new IllegalArgumentException("File already exists");
        }
        try {
            Files.createFile(newFilePath);
            return "File at " + path + FileSystems.getDefault().getSeparator()
                    + filename + " was created\n>";
        } catch (IOException e) {
            throw new IllegalArgumentException("Path does not exist");
        }
    }
}
