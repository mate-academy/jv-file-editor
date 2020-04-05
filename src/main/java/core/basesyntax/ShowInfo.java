package core.basesyntax;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShowInfo {

    public static void arguments(String comand) throws IOException {
        Pattern pattern = Pattern.compile("[\\[](.*?)[\\]]");
        Matcher matcher = pattern.matcher(comand);
        List<String> list = new ArrayList<>();
        while (matcher.find()) {
            list.add(matcher.group(1));
        }
        if (list.size() != 2) {
            throw new RuntimeException("Неправильно введено директорію або ім*я файлу");
        }
        info(list.get(0), list.get(1));
    }

    public static void info(String path, String fileName) throws IOException {
        Path adress = Paths.get(path);
        String adressToFile = path + "\\" + fileName;
        Path file = Paths.get(adressToFile);
        int symbols = 0;
        int lines = 0;
        int words = 0;
        List<String> text = Files.readAllLines(file);
        for (String s : text) {
            symbols += s.length();
            lines++;
            words += s.split(" ").length;
        }
        System.out.println("Кількість символів - " + symbols);
        System.out.println("Кількість рядків - " + lines);
        System.out.println("Кількість слів - " + words);
        System.out.println("Дата і час останньої зміни - "
                + new Date(new File(adressToFile).lastModified()));
        System.out.println("Розмір файлу - " + new File(adressToFile).length() + " байт");

    }
}
