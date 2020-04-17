package core.basesyntax;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class FileEditor {
    public void createFile(String fileName, String inputPath) {
        Path path = Paths.get(inputPath + fileName);
        if (Files.exists(path)) {
            try {
                Files.createFile(path);
                System.out.println("Successfully creation!");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("File with name \"" + fileName + "\" already exist.");
            System.out.println("Do you want to rewrite it?");
            Scanner sc = new Scanner(System.in);
            String key = sc.next();
            if (key.equalsIgnoreCase("yes")) {
                rewriteFile(path);
            }
        }
    }

    public void saveText(String fileName, String inputPath, String text) {
        try (FileWriter out = new FileWriter(inputPath + fileName)) {
            out.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void rewriteFile(Path path) {
        try {
            Files.delete(path);
            Files.createFile(path);
            System.out.println("Successfully rewrite!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void readFile(String fileName, String inputPath) {
        Path path = Paths.get(inputPath + fileName);
        try {
            List<String> lines = takeDataFromFile(path);
            if (lines.size() == 0) {
                System.out.println("empty file");
            } else {
                System.out.println("FILE CONTENTS: \n");
                for (String line : lines) {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            System.out.println("File with name \"" + e.getLocalizedMessage() + "\" is not exist.");
        }

    }

    public List<String> takeDataFromFile(Path path) throws IOException {
        return Files.readAllLines(path);
    }

    public void getInfo(String fileName, String inputPath) {
        Path path = Paths.get(inputPath + fileName);
        try {
            List<String> lines = takeDataFromFile(path);
            System.out.println("Num of lines: " + lines.size());
            System.out.println("Num of words: " + countWords(lines));
            System.out.println("Num of symbols: " + lines.toString().length());
            System.out.println("Last modified time: " + Files.getLastModifiedTime(path));
            System.out.println(Files.size(path));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public int countWords(List<String> lines) {
        String[] words = lines.toString().replaceAll("[^A-Za-zА-Яа-я ]", "").split(" ");
        return words.length;
    }
}
