package core.basesyntax;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Methods {

    protected static void createFile(String path, String filename) {
        Path paths = Paths.get(path + File.separator + filename);
        if (Files.exists(paths)) {
            System.out.println("File already exists. Would you like to rewrite it? (y/n): ");
            Scanner scanner = new Scanner(System.in);
            String result = scanner.nextLine();
//            if(result.equals("y")) {
//
//            } else if (result.equals("n")) {
//
//            } else {
//                System.out.println("Wrong input!");
//            }
        } else {
            try {
                Files.createFile(paths);
            } catch (IOException e) {
                System.out.println("Path does not exist!");
            }
            System.out.println("File created.");
        }
    }

    protected static void readFile(String path, String filename) {
        String result = "";
        System.out.println(result);
    }

    protected static void fileInfo(String path, String filename) {
        System.out.println();
    }

    protected static void help() {

    }

    protected static void helpByCommand(String command) {

    }

    protected static void saveText() {
        System.out.println("Would you like to save your text? (y/n): ");

    }
}
