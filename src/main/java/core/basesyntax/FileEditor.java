package core.basesyntax;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class FileEditor {
    public static String PATHNAME = "E:\\mate_Academy\\jv-file-editorNew"
            + "\\src\\main\\java\\core\\basesyntax\\resourses";

    public void createFile() {

        System.out.println("Enter the file name with extension : "
                + "Example: double slash names.txt");
        Scanner scanner = new Scanner(System.in);
        String nameFile = scanner.nextLine();
        File file = new File(PATHNAME + nameFile);

        try {

            if (file.createNewFile()) {
                System.out.println("File created");
            } else {
                System.out.println("File already exists");
                createFile();
            }
        } catch (IOException e) {
            System.out.println("------------------------------------------------------------");
            System.out.println("............... Incorrect input...............................");
            System.out.println("------------------------------------------------------------");
            createFile();
        }
    }

    public void readFiles() {
        try {
            System.out.println("Enter the file name with extension : "
                    + "Example: double slash names.txt");

            Scanner input = new Scanner(System.in);

            File file = new File(PATHNAME + input.nextLine());
            System.out.println("Reading.....");
            input = new Scanner(file);

            while (input.hasNextLine()) {
                String line = input.nextLine();
                System.out.println(line);
            }
            input.close();

        } catch (Exception e) {
            System.out.println("------------------------------------------------------------");
            System.out.println("....................Incorrect input..........................");
            System.out.println("------------------------------------------------------------");
            readFiles();
        }
        System.out.println("END");
    }

    public void getInfoAboutFile() {
        System.out.println("Enter the file name with extension : "
                + "Example: double slash names.txt");
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.nextLine();
        File myFile = new File(PATHNAME + fileName);
        if (myFile.exists()) {
            System.out.println("File exists");
            System.out.println("File size: " + myFile.length());
            System.out.println("Absolute path: " + myFile.getAbsolutePath());

        } else {
            System.out.println("File not found");

            if (myFile.canRead()) {
                System.out.println("File can be read");
            } else {
                System.out.println("File can not be read");
            }

            if (myFile.canWrite()) {
                System.out.println("File can be written");
            } else {
                System.out.println("File can not be written");
            }
        }

    }
}

