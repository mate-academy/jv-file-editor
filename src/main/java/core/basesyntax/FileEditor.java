package core.basesyntax;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileEditor {
    public static void main(String[] args) throws IOException {
        System.out.println("Введіть вашу команду");
        Scanner comand = new Scanner(System.in);
        String textOfComand = comand.nextLine();
        if (textOfComand.contains("create ")) {
            Creating.arguments(textOfComand);
        } else if (textOfComand.contains("read ")) {
            Reading.arguments(textOfComand);
        } else if (textOfComand.contains("info ")) {
            ShowInfo.arguments(textOfComand);
        } else if (textOfComand.contains("help")) {
            Helping.comands(textOfComand);
        } else if (textOfComand.equals("exit")) {
            System.exit(0);
        } else {
            String text = textOfComand;
            System.out.println("Бажаєте зберегти цей текст?");
            Scanner enter = new Scanner(System.in);
            if (enter.nextLine().equals("так")) {
                try (FileWriter fileWriter = new FileWriter(new File("newFile.txt"))) {
                    fileWriter.write(text);
                } catch (IOException e) {
                    throw new RuntimeException("Error while writing text to the file");
                }
            } else {
                System.exit(0);
            }
        }
    }
}
