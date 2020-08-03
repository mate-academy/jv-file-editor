package core.basesyntax;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.List;

public class DriveFileService implements FileService {

    private final Path file;

    public DriveFileService(Path file) {
        this.file = file;
    }

    @Override
    public void createFile() throws IOException {
        Files.deleteIfExists(file);
        Files.createFile(file);
    }

    @Override
    public List<String> readFile() throws IOException {
        return Files.readAllLines(file);
    }

    @Override
    public String getFileInfo() throws IOException {
        FileInfo fileInfo = new FileInfo();
        List<String> lines = readFile();
        fileInfo.numberOfChars = lines.stream()
                .map(line -> line.replaceAll("\\s", ""))
                .mapToInt(String::length)
                .sum();
        fileInfo.numberOfLines = lines.size();
        fileInfo.numberOfWords = lines.stream()
                .mapToInt(line -> line.split("[^\\wа-яА-Я]+").length)
                .sum();
        fileInfo.lastModifiedTime = Files.getLastModifiedTime(file);
        fileInfo.size = Files.size(file);
        return fileInfo.toString();
    }

    @Override
    public void saveFile(String content) throws IOException {
        Files.writeString(file, content);
    }

    private static class FileInfo {
        private static final SimpleDateFormat FORMAT
                = new SimpleDateFormat("dd-MM-yyyy 'at' HH:mm:ss");
        private int numberOfChars;
        private int numberOfLines;
        private int numberOfWords;
        private FileTime lastModifiedTime;
        private long size;

        @Override
        public String toString() {
            return String.format("File Info:"
                                 + "\n\tnumber of chars: %,d"
                                 + "\n\tnumber of lines: %,d"
                                 + "\n\tnumber of words: %,d"
                                 + "\n\tlast change: %s"
                                 + "\n\tsize: %,d bytes",
                    numberOfChars, numberOfLines, numberOfWords,
                    FORMAT.format(lastModifiedTime.toMillis()), size);
        }
    }
}
