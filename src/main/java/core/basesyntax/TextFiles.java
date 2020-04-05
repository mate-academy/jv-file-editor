package core.basesyntax;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.List;
import java.util.Scanner;

public class TextFiles {
    private static Scanner input = new Scanner(System.in);
    private static String INPUT = input.nextLine();
    private static String[] COMMANDS = {"create", "read", "info", "help", "helpC", "exit"};
    private static int CASE_NUM = 0;
    private static String[] DESCRIPTION = {" - create a new file or overrides the existing one",
            " - output the file`s text",
            " - output file`s info",
            " - output commands info",
            " - output specific command info",
            " - output info about the specific command"};

    public static void main(String[] args) throws IOException {
        System.out.println("Please enter your command");
        do {
            commandExecute();
        } while (!INPUT.equals(COMMANDS[COMMANDS.length - 1]));
    }

    public static void checkInput() {
        String[] args = INPUT.split(" ");
        if (args.length == 1) {
            if (INPUT.equals(COMMANDS[3])) {
                CASE_NUM = 4;
            } else if (INPUT.equals(COMMANDS[5])) {
                CASE_NUM = 6;
            }
        }
        if (args.length == 2) {
            for (String s : COMMANDS) {
                if (args[0].equals(COMMANDS[4]) && args[1].equals(s)) {
                    CASE_NUM = 5;
                    break;
                }
            }
        }
        if (args.length == 3) {
            for (int i = 0; i < COMMANDS.length; i++) {
                //"^([\\w\\s+\\-/]|(\\\\\\\\))+$"
                if (args[0].equals(COMMANDS[i]) && args[1].matches("^([a-zA-Z0-9 +_/-]|\\\\\\\\)+")
                        && args[2].matches("^([a-zA-Z0-9 +_/-]|\\\\\\\\)+")) {
                    CASE_NUM = i + 1;
                }
            }
        }
    }

    public static void commandExecute() throws IOException {
        String[] args = INPUT.split(" ");
        checkInput();
        switch (CASE_NUM) {
            case 1:
                create(args[1], args[2]);
                break;
            case 2:
                read(args[1], args[2]);
                break;
            case 3:
                info(args[1], args[2]);
                break;
            case 4:
                help();
                break;
            case 5:
                help(args[1]);
                break;
            default:
                exit();
                break;
        }
    }

    public static void create(String path, String fileName) throws IOException {
        try {
            if (Files.exists(Paths.get(path + File.separator + fileName))) {
                System.out.println("File already exists, do you want to rewrite it?  Y / N");
                Scanner sc = new Scanner(System.in);
                if (sc.toString().matches("Y")) {
                    Files.delete(Paths.get(path + File.separator + fileName));
                } else if (sc.toString().matches("X")) {
                    exit();
                } else {
                    System.out.println("Please enter the correct command");
                }
            }
            Files.createFile(Paths.get(path + File.separator + fileName));
        } catch (IOException e) {
            if (!Files.exists(Paths.get(path))) {
                throw new IOException("The src dir doesn't exist");
            }
        }
    }

    public static void read(String path, String fileName) throws IOException {
        try {
            List<String> lines = Files.readAllLines(Paths.get(path + File.separator + fileName));
            for (String s: lines) {
                System.out.println(s);
            }

        } catch (IOException e) {
            if (!Files.exists(Paths.get(path))) {
                throw new IOException("The src dir doesn't exist");
            }
            if (!Files.exists(Paths.get(path + File.separator + fileName))) {
                throw new IOException("The file doesn't exist");
            }
        }
    }

    public static void info(String path, String fileName) throws IOException {
        Path path1 = Paths.get(path + File.separator + fileName);
        List<String> lines = Files.readAllLines(Paths.get(path + File.separator + fileName));
        int charsCount = 0;
        int linesCount = 0;
        int wordsCount = 0;
        double fileSize = Files.size(path1);
        FileTime dataTimeLastModification = Files.getLastModifiedTime(path1);
        for (String s: lines) {
            linesCount++;
            String[] words = s.split(" ");
            int wordsCountTemp = 0;
            int charsCountTemp = 0;
            wordsCount += wordsCountTemp;
            charsCount += charsCountTemp;
            for (String w: words) {
                wordsCountTemp++;
                charsCount += w.toCharArray().length;
            }
        }
        System.out.println("There are " + charsCount + " chars" + "%n"
                + "lines " + linesCount + "%n" + "words" + wordsCount + "%n"
                + "The last modified sate was: " + dataTimeLastModification
                + "%n" + "File size " + fileSize);
    }

    public static void help() {
        for (int i = 0; i < COMMANDS.length; i++) {
            System.out.println(COMMANDS[i] + DESCRIPTION[i]);
        }
    }

    public static void help(String command) {
        for (int i = 0; i < COMMANDS.length; i++) {
            if (command.equals(COMMANDS[i])) {
                System.out.println(COMMANDS[i] + DESCRIPTION[i]);
            }
        }
    }

    public static void exit() {
        checkInput();
        if (CASE_NUM == 0) {
            System.out.println("Do you want to save the input?");
        }
    }
}
