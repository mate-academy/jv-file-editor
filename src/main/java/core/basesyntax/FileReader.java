package core.basesyntax;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileReader {
    public String read(String path, String filename) {
        StringBuilder buffer = new StringBuilder();
        Path filePath = Paths.get(path + FileSystems.getDefault().getSeparator() + filename);
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                buffer.append(currentLine).append('\n');
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("File not found");
        }
        return buffer.toString();
    }

    public String info(String path, String filename) {
        int charNumber = 0;
        int linesNumber = 0;
        int wordsNumber = 0;
        StringBuilder buffer = new StringBuilder();
        Path filePath = Paths.get(path + FileSystems.getDefault().getSeparator() + filename);
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                charNumber += currentLine.length();
                wordsNumber += currentLine.split(" ").length;
                linesNumber++;
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("File not found");
        }
        try {
            buffer.append("File ").append(filePath)
                    .append(" has following parameters:\n")
                    .append("Amount of characters: ").append(charNumber)
                    .append("\nNumber of words: ").append(wordsNumber)
                    .append("\nNumber of lines: ").append(linesNumber).append("\nSize: ")
                    .append(Files.getAttribute(filePath, "size").toString())
                    .append(" bytes\n").append("Last modification time: ")
                    .append(Files.getAttribute(filePath, "lastModifiedTime").toString())
                    .append("\n");
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot read attribute");
        }
        return buffer.toString();
    }
}
