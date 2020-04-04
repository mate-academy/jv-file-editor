package core.basesyntax.commands;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class Create extends Command {

    public static final Create INSTANCE = new Create(
            "create",
            "[path] [file-name] [text]",
            "Создает текстовый файл по указанному пути.");

    public Create(String name, String argDescription, String description) {
        super(name, argDescription, description);
    }

    @Override
    public void execute(String... args) {
        // Создает текстовый файл по указанному пути.
        // Если путь не существует, вывести соответствующее сообщение.
        // Если файл уже существует, вывести запрос на его перезапись
        if (args.length >= 3) {
            String path = args[1];
            String filename = args[2];
            String text = String.join(" ", Arrays.asList(args).subList(3, args.length));
            File file = new File(path, filename);

            if (Files.exists(Paths.get(path))) {

                String answer = "";

                if (file.exists()) {
                    System.out.println(
                            "Файл с таким именем уже существует."
                                    + " Перезаписать? (yes/no) ");
                    answer = new Scanner(System.in).nextLine().toLowerCase();
                }

                if (!file.exists() || answer.equals("yes") || answer.equals("y")) {
                    try {
                        file.createNewFile();
                        if (file.exists()) {
                            System.out.println("Файл " + filename + " успешно создан.");
                            if (!text.equals("")) {
                                try (FileWriter writer = new FileWriter(
                                        path + File.separator + filename)) {
                                    writer.write(text);
                                    System.out.println("Текст записан.");
                                }
                            }
                        } else {
                            System.out.println(
                                    "Файл " + filename + " создать не получилось.");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                System.out.println("Путь не существует.");
            }
        } else {
            System.out.println(
                    "Неверный аргумент(ы)."
                            + " Введите \"help\" для справки.");
        }
    }
}
