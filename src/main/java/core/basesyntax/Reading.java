package core.basesyntax;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reading {

    public static void arguments(String comand) throws IOException {
        Pattern pattern = Pattern.compile("[\\[](.*?)[\\]]");
        Matcher matcher = pattern.matcher(comand);
        List<String> list = new ArrayList<>();
        while (matcher.find()){
            list.add(matcher.group(1));
        }
        if (list.size() != 2) {
            throw new RuntimeException("Неправильно введено директорію або ім*я файлу");
        }
        read(list.get(0), list.get(1));
    }

    public static void read(String path, String fileName) throws IOException {
        Path adress = Paths.get(path);
        String adressToFile = path + "\\" + fileName;
        Path file = Paths.get(adressToFile);
        if (!Files.exists(adress)) {
            System.out.println("Такої директорії не існує");
        }
        if (!Files.exists(file)) {
            System.out.println("файлу з таким іменем не існує");
        }
        else {
            List<String> lines = Files.readAllLines(file);
            for (String s : lines) {
                System.out.println(s);
            }
        }
    }
}
