package core.basesyntax;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Console {
    private static final DateTimeFormatter FORMAT =
            DateTimeFormatter.ofPattern("dd.MM.yy HH:mm:ss");
    private static final HashMap<String,String> map = new HashMap<>();

    public Console() {
        map.put("help","Выводит в консоль все доступные комманды и информацию к ним.");
        map.put("create","Создает текстовый файл по указанному пути.\n"
                + "Если путь не существует, выводит соответствующее сообщение. \n"
                + "Если файл уже существует, выводть запрос на его перезапись.");
        map.put("read", "Считывает файл по указанному пути и выводит текст в консоль. \n"
                + "Если указанного пути и/или файла не существует,\n"
                + "вывести соответствующее сообщение.");
        map.put("info", "Выводит краткую информацию по указанному файлу:"
                + " количество символов, строк, слов, дату и время последнего"
                + " изменения, а также размер файла.");
        map.put("exit", "Завершение работы программы. + \\n + Инормация:\n"
                + "Если написать в консоль любой текст, который не является командой,\n"
                + "и нажать enter, должно появиться сообщение\n"
                + "с предложением сохранить текст.");
    }

    public void create(String[] path) {
        File file = new File(path[1]);
        boolean exsit = false;
        try {
            while (!exsit) {
                if (file.createNewFile()) {
                    System.out.println("File is created!");
                    exsit = true;
                } else {
                    System.out.println("File already exists.");
                    file.delete();
                    System.out.println("Recreating...DONE!");
                    exsit = false;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Wrong PATH!");
        }
    }

    public void read(String path, String name) {
        if (path.equals("exit")) {
            System.out.println("Exiting....");
        } else {
            Path pathFile = Paths.get(path + name);
            List<String> content;
            try {
                content = Files.readAllLines((pathFile));

            } catch (IOException e) {
                throw new RuntimeException("Wrong PATH!");
            }
            StringBuilder result = new StringBuilder();
            for (String a : content) {
                result.append(a).append("\n");
            }
            System.out.println(result.substring(0, result.length() - 1));
        }
    }

    public void write(String[] path, String input1) {
        if (path[1].equals("exit")) {
            System.out.println("Exiting....");
        } else {
            try {
                FileWriter file = new FileWriter(path[0] + path[1]);
                file.write(input1);
                file.flush();
                System.out.println("Your text-->" + input1 + "<--'s successfully written");
            } catch (IOException e) {
                throw new RuntimeException("Wrong name or Path of file...Try again...");
            }
        }
    }

    public void info(String[] path) {
        Path pathFile = Paths.get(path[0] + path[1]);
        List<String> reader;
        LocalDateTime date;
        try {
            date = Instant.ofEpochMilli(Files.getLastModifiedTime(pathFile).toMillis())
                    .atZone(ZoneId.systemDefault()).toLocalDateTime();
            reader = Files.readAllLines(pathFile);
        } catch (IOException e) {
            throw new RuntimeException("Wrong path or not txt format!");
        }
        int countChar = 0;
        int countWords = 0;
        for (String a : reader) {
            for (int i = 0; i < a.length(); i++) {
                countChar++;
                if ((a.charAt(i) == ' ') || ((i == a.lastIndexOf(a)))) {
                    countWords++;

                }
            }
        }
        String result = "CountOfChars: " + countChar + "\n" + "CountOfLINES: " + reader.size()
                + "\n" + "CountOfWords: " + countWords + "\n" + "DateAndTimeOfTheLastChange "
                + date.format(FORMAT) + "\n" + "FleLength: " + path[0] + path[1].length()
                + " bytes";
        System.out.println(result);
    }

    public void help() {
        String a = "help:" + "\n" + "Выводит в консоль все доступные комманды и информацию к ним."
                + "\n" + "create:" + "\n" + "Создает текстовый файл по указанному пути." + "\n"
                + "info:" + "\n" + "" + "Выводит краткую информацию по пути." + "\n"
                + "read:" + "\n" + "Считывает файл по указанному пути и выводит текст в консоль."
                + "\n" + "help(command):" + "\n" + "Выводит в консоль информацию "
                + "по указанной команде." + "\n" + "exit:" + "\n" + "Завершение работы программы.";
        System.out.println(a);
    }

    public void help(String command) {
        String answer = map.get(command);
        System.out.println(answer == null ? "Команда не опознана." : answer);
    }

    public String[] pathName() {
        Scanner scn = new Scanner(System.in);
        System.out.println("Write pls path to your file" + "\n" + "EXAMPLE:"
                + "'src/main/java/core/basesyntax'");
        String path = scn.nextLine() + File.separator;
        System.out.println("Write pls name of your file" + "\n" + "EXAMPLE:"
                + "'ExampleName'");
        String name = scn.nextLine() + ".txt";
        return new String[]{path,name};
    }
}


