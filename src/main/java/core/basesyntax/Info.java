package core.basesyntax;

import java.io.File;
import java.util.Scanner;

import static core.basesyntax.CreateFile.PATHNAME;
import static core.basesyntax.MenuController.todayDate;

public class Info {
    public static void getInfoAboutFile() {
        System.out.println("Enter the file name with extension : " + "Example: double slash names.txt");
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