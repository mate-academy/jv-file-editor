package core.basesyntax;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;
import static java.nio.file.Files.getLastModifiedTime;

/**
 * Feel free to remove this class and create your own.
 */
public class FileEditor {

    private static final DateFormat FORMAT = new SimpleDateFormat("dd MM yyyy HH:mm:ss");

    public static void main(String[] args) {
        FileEditor start = new FileEditor();
        start.FilesEditorStart();
    }

    public void FilesEditorStart() {
        System.out.println("To use the program, enter the command and press Enter");
        System.out.println("To get information about available commands write @help");
        System.out.println();
        System.out.println("To start the program from the beginning write @start");
        System.out.println();
        System.out.println("Write command pleas!");
        String inputFromConsole = new Scanner(System.in).nextLine();
        if (inputFromConsole.equalsIgnoreCase("create")) {
            createFile();
            System.out.println();
            FilesEditorStart();
        }
        if (inputFromConsole.equalsIgnoreCase("read")) {
            readFile();
            System.out.println();
            FilesEditorStart();
        }
        if (inputFromConsole.equalsIgnoreCase("info")) {
            infoAboutFile();
            System.out.println();
            FilesEditorStart();
        }
        if (inputFromConsole.equalsIgnoreCase("help")) {
            help();
            System.out.println();
            FilesEditorStart();
        }
        if (inputFromConsole.equalsIgnoreCase("start")) {
            System.out.println();
            FilesEditorStart();
        } else {
            System.out.println("Unknown command, try again");
            System.out.println();
            FilesEditorStart();
        }
        if (inputFromConsole.equalsIgnoreCase("exit")) {
            System.exit(0);
        }
    }

    private void help() {
        System.out.println("@create -creates a new file with the name entered in the console (after entering, press the enter)");
        System.out.println("@read   -read the file with the name entered in the console (after entering, press the enter)");
        System.out.println("@info   -displays information the file with the name entered in the console (after entering, press the enter)");
        System.out.println("@exit   -closes program");
        System.out.println("*the case commands does not matter");
        System.out.println("for more information write command or write @start to use the program");
        String inputFromHelp = new Scanner(System.in).nextLine();
        if (inputFromHelp.equalsIgnoreCase("start")) {
            FilesEditorStart();
        }
        if (inputFromHelp.equalsIgnoreCase("create")) {
            System.out.println("@create -creates a new file with the name entered in the console (after entering, press the enter)");
            System.out.println("if path will be not available you will be prompted to enter a new");
        }
        if (inputFromHelp.equalsIgnoreCase("read")) {
            System.out.println("@read   -read the file with the name entered in the console (after entering, press the enter)");
            System.out.println("if path will be not available you will be prompted to enter a new");
        }
        if (inputFromHelp.equalsIgnoreCase("info")) {
            System.out.println("@info   -displays information the file with the name entered in the console (after entering, press the enter)");
            System.out.println("if path will be not available you will be prompted to enter a new");
        }
        if (inputFromHelp.equalsIgnoreCase("exit")) {
            System.out.println("@exit   -closes program");
        }
    }

    private void infoAboutFile() {
        System.out.println("Write path file! to see information about the file");
        try {
            Path fileInfo = Paths.get(new Scanner(System.in).nextLine());
            List<String> stringForInfo = Files.readAllLines(fileInfo);
            int countOfSymbols = 0;
            int countOfWords = 0;
            for (String inf : stringForInfo) {
                String[] words = inf.trim().split(" ");
                for (String countWords : words) {
                    countOfWords++;
                }
                for (char a : inf.toCharArray()) {
                    countOfSymbols++;
                }
            }
            System.out.println(fileInfo.getFileName() + " file information");
            System.out.println("Count of symbols " + countOfSymbols + " symbols");
            System.out.println("Count of lines " + stringForInfo.toArray().length + " lines");
            System.out.println("Count of word  " + countOfWords + " words");
            System.out.println("Last file changes were " + FORMAT.format(getLastModifiedTime(fileInfo).toMillis()));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Wrong name.Pleas write correct name or directory");
            infoAboutFile();
        }
    }

    private void readFile() {
        System.out.println("Write path file! to read the file");
        try {
            System.out.println(toString(Files.readAllLines(Paths.get(new Scanner(System.in).nextLine()))));
        } catch (IOException e) {
            System.out.println("Wrong name.Pleas write correct name or directory");
            e.printStackTrace();
            readFile();
        }
    }

    private void createFile() {
        System.out.println("Write path file!to create the file");
        try {
            Path created = Files.createFile(Paths.get(new Scanner(System.in).nextLine()));
            if (Files.exists(Paths.get(created.toUri()))) {
                System.out.println("File created successfully");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Wrong name.Pleas write another name or existing directory");
            createFile();
        }
    }

    private String toString(List<String> list) {
        StringBuilder AllToString = new StringBuilder();
        for (String s : list) {
            AllToString.append(s).append("\n");
        }
        return AllToString.toString();
    }
}
