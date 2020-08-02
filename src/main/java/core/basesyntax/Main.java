package core.basesyntax;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class Main {

    public static final String INFO_CREATE
            = "create [path] [file-name] - Создает текстовый файл по указанному пути;";
    public static final String INFO_READ
            = "read [path] [file-name] - Считывает файл по указанному пути и "
            + "выводит текст в консоль;";
    public static final String INFO_INFO
            = "info [path] [file-name] - Выводит краткую информацию по указанному файлу;";
    public static final String INFO_HELP
            = "help - Выводит в консоль все доступные команды и информацию к ним;";
    public static final String INFO_HELP_COMMAND
            = "help [command] - Выводит в консоль информацию по указанной команде;";
    public static final String INFO_EXIT
            = "exit - Завершение работы программы.";

    public static void chooseCommand(String input) {
        String[] command = input.split(" ");
        switch (command[0]) {
            case "create":
                create(new File(command[1], command[2]));
                break;
            case "read":
                System.out.println("Содержимое файла " + "\'" + command[2] + "\' :");
                System.out.print(read(new File(command[1], command[2])));
                break;
            case "info":
                info(new File(command[1], command[2]));
                break;
            case "help":
                help(command);
                break;
            case "exit":
                System.exit(0);
                break;
            default:
                noCommand(input);
                break;
        }
    }

    public static void create(File file) {
        try {
            boolean created = file.createNewFile();
            if (created) {
                System.out.println("Файл создан");
            } else {
                System.out.println("Файл уже существует. Хотите перезаписать? Y - Да, N - Нет");
                switch (new Scanner(System.in).nextLine().toUpperCase()) {
                    case "Y":
                        file.delete();
                        System.out.println(file.createNewFile() ? "Файл перезаписан" :
                                "Системе не удалось сделать перезапись");
                        break;
                    case "N":
                        System.out.println("Вы отменили перезапись файла");
                        break;
                    default:
                        System.out.println("Неизвестная команда!");
                        break;
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static String read(File file) {
        String read = "";
        try (FileReader fileReader = new FileReader(file);
                Scanner readScanner = new Scanner(fileReader)) {
            while (readScanner.hasNextLine()) {
                read += readScanner.nextLine() + "\n";
            }
        } catch (FileNotFoundException e) {
            System.out.println("Такого файла по заданному пути не существует");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return read;
    }

    public static void info(File file) {
        StringBuilder info = new StringBuilder();
        String read = "";
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy - hh:mm:ss");
        try (FileReader fileReader = new FileReader(file);
                Scanner readScanner = new Scanner(fileReader)) {
            while (readScanner.hasNextLine()) {
                read += readScanner.nextLine() + "\n";
            }
            info.append("Информация по файлу ").append("\'").append(file.getName()).append("\':\n")
                    .append("Количество символов: ").append(read.split("").length).append("\n")
                    .append("Количество слов: ").append(read.split(" ").length).append("\n")
                    .append("Количество строк: ").append(read.split("\n").length).append("\n")
                    .append("Дата и время последнего изменения: ")
                    .append(dateFormat.format(file.lastModified())).append("\n")
                    .append("Размер файла ").append(file.length()).append(" bytes");
            System.out.println(info);
        } catch (FileNotFoundException e) {
            System.out.println("Такого файла по заданному пути не существует");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void help(String[] command) {
        if (command.length == 1) {
            StringBuilder help = new StringBuilder();
            help.append("Доступные команды: ").append("\n")
                    .append(INFO_CREATE).append("\n")
                    .append(INFO_READ).append("\n")
                    .append(INFO_INFO).append("\n")
                    .append(INFO_HELP).append("\n")
                    .append(INFO_HELP_COMMAND).append("\n")
                    .append(INFO_EXIT);
            System.out.println(help);
        } else {
            switch (command[1]) {
                case "create":
                    System.out.println(INFO_CREATE);
                    break;
                case "read":
                    System.out.println(INFO_READ);
                    break;
                case "info":
                    System.out.println(INFO_INFO);
                    break;
                case "exit":
                    System.out.println(INFO_EXIT);
                    break;
                case "help":
                    System.out.println(INFO_HELP);
                    break;
                default:
                    System.out.println("Такой команды не существует");
                    break;
            }
        }
    }

    public static void noCommand(String someText) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Хотите записать данный текст в файл?  Y - Да, N - Нет");
        switch (new Scanner(System.in).nextLine().toUpperCase()) {
            case "Y":
                String text = someText;
                System.out.println("Укажите в какой файл делать запись в "
                        + "формате [путь] [имя_файла]: ");
                String input = scanner.nextLine();
                String[] command = input.split(" ");
                File file = new File(command[0], command[1]);
                if (file.exists()) {
                    write(file, someText);
                } else {
                    System.out.println("Данного файла по заданному пути не существует");
                }
                break;
            case "N":
                System.out.println("Запись текста отменена!");
                break;
            default:
                System.out.println("Неизвестная команда!");
                break;
        }
    }

    public static void write(File file, String text) {
        String contentFile = read(file);
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(contentFile + text);
            System.out.println("Запись сделана!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            chooseCommand(scanner.nextLine());
        }
    }
}
