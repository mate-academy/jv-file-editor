package core.basesyntax;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Logic {
    public static final Command exit =
            new Command("exit",
                    "[code]",
                    "Завершение работы программы.") {
                @Override
                void execute(String... args) {
                    int code = 0;
                    if (args != null && args.length == 2) {
                        try {
                            code = Integer.parseInt(args[1]);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                    System.exit(code);
                }
            };

    public static final Command create =
            new Command("create",
                    "[path] [file-name] [text]",
                    "Создает текстовый файл по указанному пути.") {
                @Override
                void execute(String... args) {
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

                            if (!file.exists() || answer.contains("yes")) {
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
            };

    public static final Command read =
            new Command("read",
                    "[path] [file-name]",
                    "Считывает файл по указанному пути и выводит текст в консоль.") {
                @Override
                void execute(String... args) {
                    //Считывает файл по указанному пути
                    // и выводит текст в консоль.
                    // Если указанного пути и/или файла не существует,
                    // вывести соответствующее сообщение
                    if (args.length == 3) {
                        String path = args[1];
                        String filename = args[2];
                        File file = new File(path, filename);

                        if (Files.exists(Paths.get(path))) {
                            if (!file.exists()) {
                                System.out.println("Файла с таким именем не существует.");
                            } else {
                                try (BufferedReader br =
                                             new BufferedReader(
                                                     new FileReader(
                                                             path + File.separator + filename))) {
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
                        System.out.println(
                                "Неверный аргумент(ы). Введите \"help\" для справки.");
                    }
                }
            };

    public static final Command info =
            new Command("info", "[path] [file-name]",
                    "Выводит краткую информацию по указанному файлу.") {
                @Override
                void execute(String... args) {
                    if (args.length == 3) {
                        //Выводит краткую информацию по указанному файлу:
                        // количество символов,
                        // строк,
                        // слов,
                        // дату и время последнего изменения,
                        // размер файла

                        String path = args[1];
                        String filename = args[2];
                        File file = new File(path, filename);

                        if (Files.exists(Paths.get(path))) {
                            if (!file.exists()) {
                                System.out.println(
                                        "Файла с таким именем не существует.");
                            } else {
                                try (BufferedReader br = new BufferedReader(
                                        new FileReader(
                                                path + File.separator + filename))) {
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
                                    System.out.format(
                                            format, "Время последнего изменения",
                                            new SimpleDateFormat(
                                                    "HH:mm:ss dd.MM.yyy").format(
                                                            new Date(attributes
                                                                    .lastModifiedTime()
                                                                    .toMillis())));
                                    System.out.format(format,
                                            "Размер", attributes.size());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            System.out.println("Путь не существует.");
                        }
                    } else {
                        System.out.println(
                                "Неверный аргумент(ы). Введите \"help\" для справки.");
                    }
                }
            };

    public static final Command help =
            new Command("help",
                    "[command]",
                    "Выводит в консоль все доступные комманды и информацию к ним.") {
                @Override
                void execute(String... args) {
                    if (args.length == 2) {
                        switch (args[1]) {
                            case "exit": {
                                System.out.println(exit.toString());
                                break;
                            }
                            case "create": {
                                System.out.println(create.toString());
                                break;
                            }
                            case "read": {
                                System.out.println(read.toString());
                                break;
                            }
                            case "info": {
                                System.out.println(info.toString());
                                break;
                            }
                            case "help": {
                                System.out.println(help.toString());
                                break;
                            }
                            default: {
                                System.out.println("Неверный аргумент : "
                                        + args[1] + ". \"help\" для справки.");
                                break;
                            }
                        }
                    }
                    if (args.length == 1) {
                        System.out.println(create.toString());
                        System.out.println(read.toString());
                        System.out.println(info.toString());
                        System.out.println(help.toString());
                        System.out.println(exit.toString());
                    }
                }
            };
}
