package core.basesyntax;

import org.w3c.dom.ls.LSOutput;

import java.io.*;
import java.sql.SQLOutput;
import java.util.Scanner;

import static core.basesyntax.CreateFile.PATHNAME;

public class ReadFile {

    public static void readFiles() throws IOException {
        try {
            System.out.println("Enter the file name with extension : " + "Example: double slash names.txt");

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
}


