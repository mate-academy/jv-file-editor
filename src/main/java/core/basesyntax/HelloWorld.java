package core.basesyntax;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class HelloWorld {
    public static void main(String[] args) throws IOException {
        startProgram();
    }

    public static void startProgram() throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String separator = File.separator;
            String content = scanner.nextLine();
            String[] some = content.split(" ");
            switch (some[0]) {
                case "help":
                    if (some.length > 1) {
                        switch (some[1]) {
                            case "exit":
                                System.out.println("this command finish program");
                                break;
                            case "create":
                                System.out.println("this command create new file");
                                break;
                            case "read":
                                System.out.println("this command read from chose file");
                                break;
                            default:
                                System.out.println("no such command");
                        }
                    } else {
                        System.out.println("create\nread\nexit");
                    }
                    break;
                case "info":
                    if (Files.notExists(Paths.get(some[1]))) {
                        System.out.println("no such file");
                    } else {
                        System.out.println(Files.getFileStore(Paths.get(some[1])));
                    }
                    break;
                case "exit":
                    System.out.println("exit from program");
                    scanner.close();
                    break;
                case "create":
                    if (Files.exists(Paths.get(some[1]))) {
                        System.out.println("name file already exist do you"
                                + " want write in existing file (y/n)");
                        String contentForFile = scanner.nextLine();
                        String yesOrNO = scanner.nextLine();
                        if (yesOrNO.toLowerCase().equals("yes")
                                || yesOrNO.toLowerCase().equals("y")) {
                            fileWriter(contentForFile,some[1]);
                        }
                    } else {
                        Path createFile = Files.createFile(Paths.get(some[1]));
                        System.out.println("file was created");
                        String contentForFile = scanner.nextLine();
                        String yesOrNO = scanner.nextLine();
                        if (yesOrNO.toLowerCase().equals("yes")
                                || yesOrNO.toLowerCase().equals("y")) {
                            fileWriter(contentForFile,some[1]);
                        }
                    }
                    break;
                case "read":
                    readFromFile(some[1]);
                    break;
                default:
                    System.out.println("Do you want to save this:");
                    String yesOrNO = scanner.nextLine();
                    if (yesOrNO.toLowerCase().equals("yes") || yesOrNO.toLowerCase().equals("y")) {
                        System.out.println("Please enter name of file: ");
                        String filename = scanner.nextLine();
                        fileWriter(content,filename);
                    }
            }
        }
    }

    public static void fileWriter(String content, String fileName) {
        File file = new File(fileName);
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(content);
            fileWriter.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readFromFile(String fileName) {
        try {
            String line = Files.readString(Paths.get(fileName));
            System.out.println(line);
        } catch (IOException e) {
            throw new RuntimeException("No such file", e);
        }
    }
}
