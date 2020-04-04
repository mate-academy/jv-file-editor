package core.basesyntax.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Read extends Command {

    public static final Read INSTANCE = new Read(
            "read",
            "[path] [file-name]",
            "Считывает файл по указанному пути"
                    + " и выводит текст в консоль.");

    public Read(String name, String argDescription, String description) {
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
                    System.out.println("Файла с таким именем не существует.");
                } else {
                    try (BufferedReader br = new BufferedReader(
                                         new FileReader(path + File.separator + filename))) {
                        while (br.ready()) {
                            System.out.println(br.readLine());
                        }
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
