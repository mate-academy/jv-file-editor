package core.basesyntax;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface FileService {
    void createFile() throws IOException;

    List<String> readFile() throws IOException;

    String getFileInfo() throws IOException;

    void saveFile(String content) throws IOException;

    Path getFile();
}
