package core.basesyntax.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Info extends Command {

    static final Info INSTANCE = new Info(
            "info",
            "[path] [file-name]",
            "Выводит краткую информацию по указанному файлу.");

    public Info(String name, String argDescription, String description) {
        super(name, argDescription, description);
    }

    @Override
    public void execute(String... args) {
        if (args.length == 3) {

            String path = args[1];
            String filename = args[2];
            File file = new File(path, filename);

            if (Files.exists(Paths.get(path))) {
                if (!file.exists()) {
                    System.out.println(
                            "Файла с таким именем не существует.");
                } else {
                    try (BufferedReader br = new BufferedReader(
                            new FileReader(path + File.separator + filename))) {

                        List<String> lines = new ArrayList<>();

                        while (br.ready()) {
                            lines.add(br.readLine());
                        }

                        String format = "%26s  %-29s \n";

                        System.out.format(format,
                                "Количество символов", String.join("", lines).length());
                        System.out.format(format,
                                "Количество cтрок", lines.size());
                        System.out.format(format,
                                "Количество cлов", String.join(" ", lines)
                                        .replaceAll(" +", " ")
                                        .trim()
                                        .split(" ").length);

                        BasicFileAttributes attributes =
                                Files.readAttributes(
                                        Paths.get(path + File.separator + filename),
                                        BasicFileAttributes.class);
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
            } else {
                System.out.println("Путь не существует.");
            }
        } else {
            System.out.println("Неверный аргумент(ы). Введите \"help\" для справки.");
        }
    }
}
