package core.basesyntax;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

public class SimpleFileManager {

    private String readFromConsole() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please, write command and press ENTER");
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            System.out.println("Wrong input");
        }
        return "";
    }

    private void help(String[] commandSplit) {
        if (commandSplit.length == 1) {
            System.out.println("COMMAND LIST");
            System.out.println("create [path] [file-name]");
            System.out.println("read [path] [file-name]");
            System.out.println("info [path] [file-name]");
            System.out.println("help");
            System.out.println("help [command]");
            System.out.println("exit");
        } else {
            switch (commandSplit[1]) {
                case ("create") : {
                    String print = "Создает текстовый файл по указанному пути. \n"
                            + "Если путь не существует, вывести "
                            + "соответствующее сообщение. \n"
                            + "Если файл уже существует, "
                            + "вывести запрос на его перезапись\n";
                    System.out.println(print);
                    break;
                }
                case ("read") : {
                    String print = "Считывает файл по указанному пути и "
                            + "выводит текст в консоль. \n"
                            + "Если указанного пути и/или файла не существует, "
                            + "вывести соответствующее сообщение\n";
                    System.out.println(print);
                    break;
                }
                case ("info") : {
                    String print = "Выводит краткую информацию по указанному файлу: \n"
                            + "количество символов, строк, слов, дату и"
                            + "время последнего изменения, размер файла\n";
                    System.out.println(print);
                    break;
                }
                case ("help") : {
                    String print = "Выводит в консоль все доступные"
                            + " комманды и информацию к ним\n"
                            + "help [command]\n"
                            + "Выводит в консоль информацию по указанной команде\n";
                    System.out.println(print);
                    break;
                }
                case ("exit") : {
                    String print = "Завершение работы программы\n";

                    System.out.println(print);
                    break;
                }
                default: {
                    System.out.println("unknown command \n"
                            + "Если написать в консоль любой текст, "
                            + "который не является командой, и нажать enter, \n"
                            + "должно появиться сообщение с"
                            + " предложением сохранить текст\n");
                    break;
                }
            }
        }
    }

    private void executeCommand(String command) {
        String[] commandSplit = command.split(" ");
        switch (commandSplit[0]) {
            case ("help") : {
                help(commandSplit);
                break;
            }
            case ("exit") : {
                System.out.println("Bye!!!");
                System.exit(0);
                break;
            }
            case ("create") : {
                if (commandSplit.length == 3) {
                    create(commandSplit[1], commandSplit[2]);
                } else {
                    System.out.println("Wrong path.");
                }
                break;
            }
            case ("read") : {
                if (commandSplit.length == 3) {
                    read(commandSplit[1], commandSplit[2]);
                } else {
                    System.out.println("Nothing to read.");
                }
                break;
            }
            case ("info") : {
                if (commandSplit.length == 3) {
                    info(commandSplit[1], commandSplit[2]);
                } else {
                    System.out.println("Created nothing.");
                }
                break;
            }
            default: {
                System.out.println("wrong input");
                System.out.println("maybe you want to save this DATA\n"
                        + "press \"y\" to save\n"
                        + "press anything to delete all what you wrote\n"
                        + "be careful with answer\n"
                        + "I'M ASKING ONLY ONCE");
                String yesOrNo = readFromConsole();
                if (yesOrNo.equals("y")) {
                    create(command);
                }
                break;
            }
        }
    }

    private void info(String path, String name) {
        String fileName = path + name;
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(fileName));
            System.out.println("Count of symbols = "
                    + lines.toString().length());
            System.out.println("Count of lines   = "
                    + lines.size());
            System.out.println("Count of words = "
                    + lines.toString().split("\\s+").length);
            Date date = new Date(Files.getLastModifiedTime(Paths.get(fileName)).toMillis());
            System.out.println(String.format("Last time was modified = "
                    + date.toString()));
            File file = new File(fileName);
            System.out.println("File size in bytes = "
                    + file.length());
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    private void read(String path, String name) {
        String fileName = path + name;
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(fileName));
            for (String line : lines) {
                System.out.println(line);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private void create(String path, String name) {

        System.out.print("your path exist ? - ");
        System.out.println(Files.exists(Paths.get(path)));

        if (Files.exists(Paths.get(path))) {
            try {
                if (Files.exists(Paths.get(path + name))) {
                    System.out.println("File Already Exists.");
                    System.out.println("Do you want to rewrite it? y/n");
                    String yesOrNo = readFromConsole();
                    if (yesOrNo.equals("y")) {
                        Files.writeString(Paths.get(path + name),"");
                    }
                } else {
                    Path testFile1 = Files.createFile(Paths.get(path + name));
                    System.out.print("file crated ? - ");
                    System.out.println(Files.exists(Paths.get(path + name)));
                }

            } catch (IOException e) {
                System.out.println("something was wrong");
            }
        }
    }

    private void create(String command) {
        System.out.println("where you want to save your data?\n"
                + "write \"PATH\" + \" \" + \"FILE.EXTENSION\"");
        String[] pathAndName = readFromConsole().split(" ");
        if (pathAndName.length != 2) {
            System.out.println("wrong Path.Name");
        } else {
            if (!Files.exists(Paths.get(pathAndName[0] + pathAndName[1]))) {
                create(pathAndName[0], pathAndName[1]);
            } else {
                write(pathAndName[0], pathAndName[1], command);
            }
        }

    }

    private boolean write(String path, String name, String data) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            List<String> lists = Files.readAllLines(Paths.get(path + name));
            for (String list : lists) {
                stringBuilder.append(list).append("\n");
            }
            stringBuilder.append(data).append("\n");
            Path testFile1 = Files.writeString(Paths.get(path + name), stringBuilder.toString());
            System.out.println("Write was successful.");
            return true;
        } catch (IOException e) {
            System.out.println(e.toString());
            return false;
        }
    }

    public void startProgram() {
        {
            System.out.println("Program start");
            System.out.println("press \"help\" if you know nothing");
        }
        String command = "";
        while (!command.equals("exit")) {
            command = readFromConsole();
            executeCommand(command);
        }
    }
}
