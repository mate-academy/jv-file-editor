package core.basesyntax;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileEditor {

    private static final String SEPARATOR = File.separator;
    private static final DateTimeFormatter DATE_TIME_FORMATTER
            = DateTimeFormatter.ofPattern("dd.MM.yyyy H.m.s");

    public static void create(String path, String fileName) throws IOException {
        Path directoryPath = Paths.get(path);
        Path filePath = Paths.get(path + SEPARATOR + fileName);

        if (Files.exists(directoryPath)) {
            if (Files.notExists(filePath)) {
                Files.createFile(filePath);
            } else {
                System.out.printf("File with \"%s\" already exist. Overwrite?(y/n)", fileName);
                Scanner scanner = new Scanner(System.in);
                boolean correctInput = false;
                while (!correctInput) {
                    String input = scanner.next();
                    switch (input) {
                        case "y" :
                            Files.delete(filePath);
                            Files.createFile(filePath);
                            correctInput = true;
                            break;
                        case "n":
                            correctInput = true;
                            break;
                        default:
                            System.out.println("Wrong input. Try again.(y/n)");
                            break;
                    }
                }
            }
        } else {
            System.out.println("This path is not exist.");
        }
        System.out.print(Files.exists(filePath) ? "File created.\n" : "");
    }

    public static void read(String path, String fileName) throws IOException {
        Path filePath = Paths.get(path + SEPARATOR + fileName);

        if (Files.exists(filePath)) {
            Stream<String> allLines = Files.lines(filePath);
            System.out.println(allLines.collect(Collectors.joining("\n")));
        } else {
            System.out.printf("File \"%s\" is not exist.", filePath);
        }
    }

    public static void info(String path, String fileName) throws IOException {
        Path filePath = Paths.get(path + SEPARATOR + fileName);
        long numberOfChars = 0;
        long numberOfLines = 0;
        long numberOfWords = 0;
        long size = 0;
        LocalDateTime lastModifiedDate = LocalDateTime
                .ofInstant(Files.getLastModifiedTime(filePath).toInstant(),
                        ZoneId.systemDefault()); ;

        if (Files.exists(filePath)) {
            List<String> allLines = Files.readAllLines(filePath);

            for (String line: allLines) {
                numberOfChars += line.length();
                numberOfWords += line.split(" ").length;
                numberOfLines++;
            }
            System.out.println("Info:\n"
                    + "Number of chars: " + numberOfChars + "\n"
                    + "Number of lines: " + numberOfLines + "\n"
                    + "Number of words: " + numberOfWords + "\n"
                    + "Modified: " + lastModifiedDate.format(DATE_TIME_FORMATTER) + "\n"
                    + "Size: " + Files.size(filePath) + " bytes");
        } else {
            System.out.printf("File \"%s\" is not exist.", filePath);
        }

    }
}
