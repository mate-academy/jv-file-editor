package core.basesyntax;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Date;

public class Information implements Action {

    private String path;
    private String name;

    @Override
    public void doAction(String command) {
        String[] commandSplited = command.split(" ");
        if (commandSplited.length != 3) {
            throw new RuntimeException("Нужно иное количество аргументов!");
        } else {
            path = commandSplited[1].trim();
            name = commandSplited[2].trim();
        }
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

    public void info(int symbolsCount, int linesCount, int wordsCount,
                     Date lastModified, double size) {
        System.out.println("Информация о файле" + "\n"
                + "Количество символов: " + symbolsCount + "\n"
                + "Количество строк: " + linesCount + "\n" + "Количество слов: "
                + wordsCount + "\n" + "Файл изменялся последний раз: "
                + lastModified + "\n" + "Размер файла: " + size);
    }
}
