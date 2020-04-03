package core.basesyntax;

import org.w3c.dom.ls.LSOutput;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Scanner;

/**
 * Feel free to remove this class and create your own.
 */
public class FileEditor {

    public void FilesEditorStart() {
        System.out.println("Write command pleas!");
        Scanner input = new Scanner(System.in);
        String inputFromConsole = input.nextLine();
        if (inputFromConsole.equals("create")) {
            createFile();
            FilesEditorStart();
        }
        if (inputFromConsole.equals("read")) {
            readFile();
            FilesEditorStart();
        }
    }

    private void readFile() {
        System.out.println("Write path file!");
        try {
            System.out.println(toString(Files.readAllLines(Paths.get(new Scanner(System.in).nextLine()))));
        } catch (IOException e) {
            System.out.println("Wrong name.Pleas write correct name or directory");
            e.printStackTrace();
            readFile();
        }
    }

    private void createFile() {
        System.out.println("Write path file!");
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

    public static void main(String[] args) {
        FileEditor start = new FileEditor();
        start.FilesEditorStart();
    }

}
