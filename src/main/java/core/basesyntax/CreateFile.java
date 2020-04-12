package core.basesyntax;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class CreateFile {
    public static String PATHNAME = "E:\\mate_Academy\\jv-file-editorNew" +
            "\\src\\main\\java\\core\\basesyntax\\resourses";

    public static void createFile() {

        System.out.println("Enter the file name with extension : " + "Example: double slash names.txt");
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
            //e.printStackTrace();
            System.out.println("------------------------------------------------------------");
            System.out.println("............... Incorrect input...............................");
            System.out.println("------------------------------------------------------------");
            createFile();
        }
    }
}
