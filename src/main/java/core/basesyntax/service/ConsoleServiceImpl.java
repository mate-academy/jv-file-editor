package core.basesyntax.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ConsoleServiceImpl implements ConsoleService {

    @Override
    public void create(String... args) {
        if (args.length < 3) {
            System.out.println("Неверный аргумент(ы). Введите \"help\" для справки.");
            return;
        }

        String path = args[1];
        String filename = args[2];
        String text = String.join(" ", Arrays.asList(args).subList(3, args.length));
        File file = new File(path, filename);
        String ans = "";

        if (!Files.exists(Paths.get(path))) {
            System.out.println("Путь не существует.");
            return;
        }

        if (file.exists()) {
            System.out.println("Файл с таким именем уже существует."
                    + " Перезаписать? (yes/no) ");
            ans = new Scanner(System.in).nextLine().toLowerCase();
        }

        if (!file.exists() || ans.equalsIgnoreCase("yes") || ans.equalsIgnoreCase("y")) {
            try {
                if (file.createNewFile()) {
                    System.out.println("Файл успешно создан.");
                }
                writeTextToFile(text, path + File.separator + filename);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void execDefaultCommand(String... args) {
        if (!args[0].equals("")) {
            System.out.println("Хотите записать текст? (yes/no)");
            String ans = new Scanner(System.in).nextLine().toLowerCase();

            if (ans.equalsIgnoreCase("yes") || ans.equalsIgnoreCase("y")) {
                System.out.println("Введите путь и имя файла [path] [filename]: ");
                String ansPathAndFilename = new Scanner(System.in).nextLine().toLowerCase();
                String[] splitData = ansPathAndFilename.split(" ");
                if (splitData.length == 2) {
                    String newCommand =
                            "create "
                                    + splitData[0] + " "
                                    + splitData[1] + " "
                                    + args[0];
                    create(newCommand.split(" "));
                } else {
                    System.out.println("Неверный аргумент(ы). "
                            + "Введите \"help\" для справки.");
                }
            }
        }
    }

    @Override
    public void exit() {
        System.exit(0);
    }

    @Override
    public void help(String... args) {
        if (args.length == 2) {
            String msg = "";
            switch (args[1]) {
                case "exit": {
                    msg = String.format("%7s   %-25s   %-20s", args[1], "[code]",
                            "Завершение работы программы.");
                    break;
                }
                case "create": {
                    msg = String.format("%7s   %-25s   %-20s", args[1], "[path] [file-name] [text]",
                            "Создает текстовый файл по указанному пути.");
                    break;
                }
                case "read": {
                    msg = String.format("%7s   %-25s   %-20s", args[1], "[path] [file-name]",
                            "Считывает файл по указанному пути" + " и выводит текст в консоль.");
                    break;
                }
                case "info": {
                    msg = String.format("%7s   %-25s   %-20s", args[1], "[path] [file-name]",
                            "Выводит краткую информацию по указанному файлу.");
                    break;
                }
                case "help": {
                    msg = String.format("%7s   %-25s   %-20s", args[1], "[command]",
                            "Выводит в консоль " + "все доступные комманды и информацию к ним.");
                    break;
                }
                default: {
                    System.out.println("Неверный аргумент : "
                            + args[1] + ". \"help\" для справки.");
                    break;
                }
            }
            if (msg.length() > 0) {
                System.out.println(msg);
            }
        }
        if (args.length == 1) {
            List<String> commands = List.of("create", "read", "info", "help", "exit");
            for (String s : commands) {
                help("help", s);
            }
        }
    }

    @Override
    public void info(String... args) {
        if (!isPathAndFileExist(args)) {
            return;
        }

        Path path = Paths.get(args[1] + File.separator + args[2]);
        try {
            List<String> lines = new ArrayList<>(Files.readAllLines(path));
            String format = "%26s  %-29s \n";
            System.out.format(format, "Количество символов", String.join("", lines).length());
            System.out.format(format, "Количество cтрок", lines.size());
            System.out.format(format, "Количество cлов", String.join(" ", lines)
                    .replaceAll(" +", " ")
                    .trim()
                    .split(" ").length);

            BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);
            System.out.format(format, "Время последнего изменения",
                    new SimpleDateFormat("HH:mm:ss dd.MM.yyy")
                            .format(new Date(
                                    attributes
                                            .lastModifiedTime()
                                            .toMillis())));
            System.out.format(format, "Размер", attributes.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void read(String... args) {
        if (!isPathAndFileExist(args)) {
            return;
        }

        Path path = Paths.get(args[1] + File.separator + args[2]);
        try {
            Files.readAllLines(path).forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeTextToFile(String text, String path) throws IOException {
        if (text.equals("")) {
            return;
        }
        try (FileWriter writer = new FileWriter(path)) {
            writer.write(text);
            System.out.println("Текст записан.");
        }
    }

    private boolean isPathAndFileExist(String... args) {
        if (args.length < 3) {
            System.out.println("Неверный аргумент(ы). Введите \"help\" для справки.");
            return false;
        }
        Path path = Paths.get(args[1] + File.separator + args[2]);
        if (!Files.exists(Paths.get(args[1]))) {
            System.out.println("Путь не существует.");
            return false;
        }
        if (!Files.exists(path)) {
            System.out.println("Файла с таким именем не существует.");
            return false;
        }
        return true;
    }
}
