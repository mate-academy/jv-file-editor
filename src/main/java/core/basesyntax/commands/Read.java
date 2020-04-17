package core.basesyntax.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

class Read extends Command {

    static final Read INSTANCE = new Read(
            "read",
            "[path] [file-name]",
            "Считывает файл по указанному пути"
                    + " и выводит текст в консоль.");

    public Read(String name, String argDescription, String description) {
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
            System.out.println(lines);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
