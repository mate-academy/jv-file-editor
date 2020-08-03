package core.basesyntax;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Feel free to remove this class and create your own.
 * <p>
 * jv-file-editor
 * Написать небольшое консольное приложение для работы с текстовыми файлами,
 * которое будет обладать следующими командами:
 * <p>
 * create [path] [file-name]
 * Создает текстовый файл по указанному пути. Если путь не существует,
 * вывести соответствующее сообщение. Если файл уже существует, вывести
 * запрос на его перезапись
 * <p>
 * read [path] [file-name]
 * Считывает файл по указанному пути и выводит текст в консоль. Если
 * указанного пути и/или файла не существует, вывести соответствующее сообщение
 * <p>
 * info [path] [file-name]
 * Выводит краткую информацию по указанному файлу: количество символов, строк,
 * слов, дату и время последнего изменения, размер файла
 * <p>
 * help
 * Выводит в консоль все доступные комманды и информацию к ним
 * <p>
 * help [command]
 * Выводит в консоль информацию по указанной команде
 * <p>
 * exit
 * Завершение работы программы
 * <p>
 * Если написать в консоль любой текст, который не является командой,
 * и нажать enter, должно появиться сообщение с предложением сохранить текст
 */

class FileReader {
    private static final String PATH_EXAMPLE = "D:\\\\java\\\\file-editor";
    private static final String FILE_EXAMPLE = "file-editor";
    private static List<String> commandList = new ArrayList<>(
            Arrays.asList("create", "read", "info", "help", "help c", "exit"));
    private String filePath = "";
    private String fileName = "";

    private String input() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("IOException", e);
        }
    }

    private boolean createFile(String filePath, String fileName) {
        if (!Files.exists(Paths.get(filePath))
                && Files.isDirectory(Paths.get(filePath))) {
            System.out.println("=== Path is not exist ===");
            return false;
        } else if (Files.exists(Paths.get(filePath + "\\" + fileName))) {
            System.out.println("=== Would you rewrite file ===\nY / N");
            while (true) {
                if (input().equals("N")) {
                    return false;
                } else if (input().equals("Y")) {
                    try {
                        Files.createFile(Paths.get(filePath + "\\" + fileName));
                        break;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    System.out.println("=== Incorrect input ===");
                }
            }
        } else {
            try {
                Files.createFile(Paths.get(filePath + "\\" + fileName));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }

    private boolean readFile(String filePath, String fileName) {
        if (!Files.exists(Paths.get(filePath))) {
            System.out.println("=== Path is not exist ===");
            return false;
        } else if (!Files.exists(Paths.get(filePath + "\\" + fileName))) {
            System.out.println("=== File is not exist ===");
            return false;
        } else {
            try {
                if (Files.readString(Paths.get(filePath + "\\" + fileName)).equals("")) {
                    System.out.println("=== File is empty ===");
                } else {
                    System.out.println(Files.readString(Paths.get(filePath + "\\" + fileName)));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }

    private boolean info(String filePath, String fileName) {
        if (!Files.exists(Paths.get(filePath))) {
            System.out.println("=== Path is not exist ===");
            return false;
        } else if (!Files.exists(Paths.get(filePath + "\\" + fileName))) {
            System.out.println("=== File is not exist ===");
            return false;
        } else {
            try {
                System.out.println("Number of character: "
                        + countChar(Files.readString(Paths.get(filePath + "\\" + fileName)))
                        + "\nNumber of strings: "
                        + Files.readAllLines(Paths.get(filePath + "\\" + fileName)).size()
                        + "\nNumber of words: "
                        + Files.readString(Paths.get(filePath + "\\" + fileName)).split(" ").length
                        + "\nDate of last change: "
                        + Files.readAllLines(Paths.get(filePath + "\\" + fileName)).toString()
                        + "\nFile size: "
                        + Files.size(Paths.get(filePath + "\\" + fileName)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }

    private void help() {
        System.out.println("create [path] [file-name]:"
                + "\n\tСоздает текстовый файл по указанному пути. "
                + "Если путь не существует, вывести соответствующее сообщение. "
                + "Если файл уже существует, вывести запрос на его перезапись"
                + "\nread [path] [file-name]:"
                + "\n\tСчитывает файл по указанному пути и выводит текст в консоль. "
                + "Если указанного пути и/или файла не существует, вывести соответствующее "
                + "сообщение"
                + "\ninfo [path] [file-name]:"
                + "\n\tВыводит краткую информацию по указанному файлу: количество символов, "
                + "строк, слов, дату и время последнего изменения, размер файла"
                + "\nhelp:"
                + "\n\tВыводит в консоль все доступные комманды и информацию к ним"
                + "\nhelp [command]:"
                + "\n\tВыводит в консоль информацию по указанной команде"
                + "\nexit:"
                + "\n\tЗавершение работы программы");
    }

    private void helpCommand(String command) {
        switch (command) {
            case "create":
                System.out.println("create [path] [file-name]:"
                        + "\n\tСоздает текстовый файл по указанному пути. "
                        + "Если путь не существует, вывести соответствующее сообщение. "
                        + "Если файл уже существует, вывести запрос на его перезапись");
                break;
            case "read":
                System.out.println("read [path] [file-name]:"
                        + "\n\tСчитывает файл по указанному пути и выводит текст в консоль. "
                        + "Если указанного пути и/или файла не существует, вывести соответствующее "
                        + "сообщение");
                break;
            case "info":
                System.out.println("info [path] [file-name]:"
                        + "\n\tВыводит краткую информацию по указанному файлу: количество символов,"
                        + " строк, слов, дату и время последнего изменения, размер файла");
                break;
            case "help":
                System.out.println("help:"
                        + "\n\tВыводит в консоль все доступные комманды и информацию к ним");
                break;
            case "exit":
                System.out.println("exit:"
                        + "\n\tЗавершение работы программы");
                break;
            default:
                break;
        }
    }

    private int countChar(String fileContent) {
        int countSymbol = 0;
        for (int i = 0; i < fileContent.length(); i++) {
            if (fileContent.charAt(i) != ' ') {
                countSymbol++;
            }
        }
        return countSymbol;
    }

    public void view() {
        System.out.println("Chose operation:"
                + "\ncreate"
                + "\nread"
                + "\ninfo"
                + "\nhelp"
                + "\nhelp c"
                + "\nexit");
        String inputCommand = input();
        switch (inputCommand) {
            case "create":
                System.out.println("Input path for create file in form like:"
                        + "\n" + PATH_EXAMPLE);
                filePath = input();
                System.out.println("Input name of file for create in form like:"
                        + "\n" + FILE_EXAMPLE);
                fileName = input();
                createFile(filePath, fileName);
                view();
                break;
            case "read":
                System.out.println("Input path for read file in form like:"
                        + "\n" + PATH_EXAMPLE);
                filePath = input();
                System.out.println("Input name of file for read in form like:"
                        + "\n" + FILE_EXAMPLE);
                fileName = input();
                readFile(filePath, fileName);
                view();
                break;
            case "info":
                System.out.println("Input path for get info in form like:"
                        + "\n" + PATH_EXAMPLE);
                filePath = input();
                System.out.println("Input name of file for get info in form like:"
                        + "\n" + FILE_EXAMPLE);
                fileName = input();
                info(filePath, fileName);
                view();
                break;
            case "help":
                help();
                view();
                break;
            case "help c":
                System.out.println("Input command for more info:");
                helpCommand(input());
                view();
                break;
            case "exit":
                System.exit(0);
                break;
            default:
                commandList.add(inputCommand);
                System.out.println("New command was added to commandList");
                view();
                break;
        }
    }
}
