package core.basesyntax.commands;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

class Create extends Command {

    static final Create INSTANCE = new Create(
            "create",
            "[path] [file-name] [text]",
            "Создает текстовый файл по указанному пути.");

    public Create(String name, String argDescription, String description) {
        super(name, argDescription, description);
    }

    @Override
    public void execute(String... args) {

        if (args.length < 3) {
            System.out.println("Неверный аргумент(ы). Введите \"help\" для справки.");
            return;
        }

        String path = args[1];
        String filename = args[2];
        String text = String.join(" ", Arrays.asList(args).subList(3, args.length));
        File file = new File(path, filename);

        if (!Files.exists(Paths.get(path))) {
            System.out.println("Путь не существует.");
            return;
        }

        String answer = "";
        if (file.exists()) {
            System.out.println("Файл с таким именем уже существует."
                    + " Перезаписать? (yes/no) ");
            answer = new Scanner(System.in).nextLine().toLowerCase();
        }

        if (!file.exists() || answer.equals("yes") || answer.equals("y")) {
            try {
                file.createNewFile();
                writeTextToFile(text, path + File.separator + filename);
            } catch (IOException e) {
                e.printStackTrace();
            }
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
}

