package core.basesyntax;

import java.io.*;

public class ReadFile {

    public static void readFiles() throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        FileInputStream fileInputStream = null;
        //FileOutputStream fileOutputStream = null;
        while (true) {
            try {
                String file1 = reader.readLine();
                fileInputStream = new FileInputStream(file1);
                System.out.println("File found -  "+ fileInputStream);
                break;
            } catch (FileNotFoundException e) {
                System.out.println("Файл не існує");
                System.out.println("Введіть правильний шлях");
            }
        }
    }
}
