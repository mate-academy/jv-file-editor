package core.basesyntax;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Date;

public interface Utilities extends Texts {

    default void information(String path, String name) {
        File file = new File(path + File.separator + name);
        StringBuilder builder = new StringBuilder();
        try (FileInputStream stream = new FileInputStream(path + File.separator + name);
                LineNumberReader numberReader = new LineNumberReader(new FileReader(file))) {
            int appendix;
            int countChars;
            int countLines = 0;
            int countWords = 1;
            while ((appendix = stream.read()) != -1) {
                builder.append(Character.toString((char) appendix));
                if ((char) appendix == ' ') {
                    countWords++;
                }
            }
            countChars = builder.toString().length();
            while (numberReader.readLine() != null) {
                countLines++;
            }
            Date date = new Date(file.lastModified());
            double size = (double) file.length();
            info(countChars, countLines, countWords, date, size);
        } catch (IOException e) {
            throw new RuntimeException("Такого файла не существует!", e);
        }
    }

    default void read(String path, String name) {
        try (FileInputStream stream = new FileInputStream(path + File.separator
                + name); BufferedInputStream bufStream = new BufferedInputStream(stream, 200)) {
            int i;
            System.out.println("Текст из файла:");
            while ((i = bufStream.read()) != -1) {
                System.out.print((char) i);
            }
        } catch (IOException e) {
            throw new RuntimeException("Такого файла не существует!", e);
        }
    }

    default void create(String path, String name) {
        try {
            File file = new File(path + File.separator + name);
            file.createNewFile();
            System.out.println("Файл успешно создан!");
        } catch (IOException e) {
            throw new RuntimeException("Такого пути не существует!", e);
        }
    }

    default void appendTextToFile(String text) {
        File file = new File("text.txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("Такого пути не существует!", e);
        }
        try (FileOutputStream stream = new FileOutputStream("text.txt", true);) {
            stream.write(text.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Такого файла не существует!", e);
        }
        System.out.println("Записать текст в новый файл в директории программы? /ДА /НЕТ");
    }
}
