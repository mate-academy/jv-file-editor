package core.basesyntax;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Command {

    public void fileReader() throws IOException {
        System.out.println("Введіть вашу команду");
        Scanner comand = new Scanner(System.in);
        String textOfComand = comand.nextLine();

        switch (textOfComand.split(" ")[0]) {
            case "create":
                create(direction(textOfComand));
                break;
            case "read":
                read(direction(textOfComand));
                break;
            case "info":
                info(direction(textOfComand));
                break;
            case "help":
                help(textOfComand);
                break;
            case "exit":
                System.exit(0);
                break;
            default:
                saveText(textOfComand);
        }
    }

    private static String direction(String textOfComand) {
        Pattern pattern = Pattern.compile("[\\[](.*?)[\\]]");
        Matcher matcher = pattern.matcher(textOfComand);
        List<String> list = new ArrayList<>();
        while (matcher.find()) {
            list.add(matcher.group(1));
        }
        if (list.size() != 2) {
            throw new RuntimeException("Неправильно введено директорію або ім*я файлу");
        }
        return list.get(0) + "\\" + list.get(1);
    }

    private void create(String adressToFile) throws IOException {
        Path adress = Paths.get(adressToFile.substring(0, adressToFile.indexOf('\\')));
        Path file = Paths.get(adressToFile);
        if (!Files.exists(adress)) {
            System.out.println("Такої директорії не існує");
        }
        if (Files.exists(file)) {
            System.out.println("Такий файл вже існує, якщо хочете його перезаписати введіть - так");
            Scanner enter = new Scanner(System.in);
            if (enter.nextLine().equals("так")) {
                Files.delete(file);
                Files.createFile(file);
            }
        } else {
            Files.createFile(file);
        }
    }

    private void read(String adressToFile) throws IOException {
        Path adress = Paths.get(adressToFile.substring(0, adressToFile.indexOf('\\')));
        Path file = Paths.get(adressToFile);
        if (!Files.exists(adress)) {
            System.out.println("Такої директорії не існує");
        }
        if (!Files.exists(file)) {
            System.out.println("файлу з таким іменем не існує");
        } else {
            List<String> lines = Files.readAllLines(file);
            for (String s : lines) {
                System.out.println(s);
            }
        }
    }

    private void info(String adressToFile) throws IOException {
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

    private void help(String comand) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("help.txt"));

        switch (comand) {
            case "help":
                for (String s : lines) {
                    System.out.println(s);
                }
                break;
            case "help [create]":
                System.out.println(lines.get(0));
                break;
            case "help [read]":
                System.out.println(lines.get(1));
                break;
            case "help [info]":
                System.out.println(lines.get(2));
                break;
            case "help [help]":
                System.out.println(lines.get(3));
                break;
            case "help [command]":
                System.out.println(lines.get(4));
                break;
            case "help [exit]":
                System.out.println(lines.get(5));
                break;
            default:
                System.out.println("Такої команди не існує!");
        }
    }

    private void saveText(String textOfComand) throws IOException {
        System.out.println("Бажаєте зберегти цей текст?");
        Scanner enter = new Scanner(System.in);
        if (enter.nextLine().equals("так")) {
            System.out.println("Вкажіть директорію файлу та його назву в форматі "
                    + "- [директорія] [ім'я]");
            Scanner scanner = new Scanner(System.in);
            String adress = scanner.nextLine();
            create(direction(adress));
            try (FileWriter fileWriter = new FileWriter(new File(direction(adress)))) {
                fileWriter.write(textOfComand);
            } catch (IOException e) {
                throw new RuntimeException("Error while writing text to the file");
            }
        } else {
            System.exit(0);
        }
    }
}
