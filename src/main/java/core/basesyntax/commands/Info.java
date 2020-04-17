package core.basesyntax.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

        if (args.length < 3) {
            System.out.println("Неверный аргумент(ы). Введите \"help\" для справки.");
            return;
        }

        Path path = Paths.get(args[1] + File.separator + args[2]);

        if (!Files.exists(Paths.get(args[1]))) {
            System.out.println("Путь не существует.");
        }

        if (!Files.exists(path)) {
            System.out.println("Файла с таким именем не существует.");
        }

        try {
            List<String> lines = Files.readAllLines(path).stream().collect(Collectors.toList());
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
}
