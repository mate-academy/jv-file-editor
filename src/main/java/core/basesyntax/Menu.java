package core.basesyntax;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Menu {
    FileEditor fileEditor = new FileEditor();

    public Menu() {
        System.out.println("******************************************************");
        welcome();
        System.out.println("******************************************************");
        helpOrWork();
    }

    private void welcome() {
        System.out.println("Welcome to FileEditor!");
    }

    public void helpOrWork() {
        System.out.println("Do you want info - 0");
        System.out.println("Do you want fileWork - 1");
        System.out.println("Exit - 2");
        System.out.println("Pleas, enter a number: ");
        Scanner scanner = new Scanner(System.in);
        int num = 0;
        if (scanner.hasNextInt()) {
            num = scanner.nextInt();
            if (num == 0 || num == 1 || num == 2) {
                switch (num) {
                    case 0:
                        help();
                        break;
                    case 1:
                        fileWork();
                        break;
                    case 2:
                        return;
                    default:
                }
            } else {
                unCorrect(num);
                helpOrWork();
            }
        } else {
            unCorrect(num);
            helpOrWork();
        }
    }

    public void help() {
        System.out.println("HELP: enter a number, which information you want know more");
        System.out.println("1. Create directory.");
        System.out.println("2. Create file.");
        System.out.println("3. Write file.");
        System.out.println("4. Read file.");
        System.out.println("5. Info file.");
        System.out.println("6. Exit.");
        System.out.println("Enter a number: ");
        chooseOption(true, 6);
    }

    public void fileWork() {
        System.out.println("Enter a number, what you want to do: ");
        System.out.println("1. Create directory.");
        System.out.println("2. Create file.");
        System.out.println("3. Write file.");
        System.out.println("4. Read file.");
        System.out.println("5. Info file.");
        System.out.println("6. Exit.");
        System.out.println("Enter a number: ");
        chooseOption(false, 6);
    }

    public void chooseOption(boolean flag, int menuRange) {
        Scanner scanner = new Scanner(System.in);
        int option = 0;
        if (scanner.hasNextInt()) {
            option = scanner.nextInt();
        }
        if ((option > 0) && (option < menuRange + 1)) {
            if (flag == true) {
                helpCommand(option);
            } else {
                workCommand(option);
            }
            wrongOption(option, flag, menuRange);
        }
    }

    public void wrongOption(int option, boolean flag, int menuRange) {
        if (option == 0) {
            unCorrect(0);
            if (flag == true) {
                helpCommand(option);
            } else {
                workCommand(option);
            }
        }
        if (option < 0 && option > 6) {
            unCorrect(option);
            if (flag == true) {
                helpCommand(option);
            } else {
                workCommand(option);
            }
        }
    }

    public void helpCommand(int option) {
        switch (option) {
            case 1:
                System.out.println("Надо ввести название "
                        + "папки для хранения файлов. \n"
                        + "Если папка с таким именем уже существует, "
                        + "программа предложит \n"
                        + "создать новую папку с другим именем.");
                System.out.println("");
                helpOrWork();
                break;
            case 2:
                System.out.println("Надо ввести название папки "
                        + "в которой будет создан файл. \n"
                        + "Если такая папка не существует, "
                        + "программа предложит создать эту папку. \n"
                        + "Далее вводим имя файла. Если такого файла нет, "
                        + "то в выбранной папке создастся \n"
                        + "новый файл. Если файл уже существуе, "
                        + "то будет предложено три варианта: \n"
                        + "1 - копировать файл, 2 - оистить "
                        + "содержимое текущего файла, \n"
                        + "3 - создать новый файл с другим именем.");
                System.out.println("");
                helpOrWork();
                break;
            case 3:
                System.out.println("Надо ввести название папки, "
                        + "в которой хранится файл для записи. \n"
                        + "Если папки с таким именем не существует,"
                        + "программа предложит создать эту папку.\n"
                        + "Далее вводим имя файла для записи. "
                        + "Если такого файла нет, программа предложит \n"
                        + "создать этот файл. Если файл существуе, "
                        + "то производится запись в файл строки \n"
                        + "из консоли до нажатия клавиши enter.");
                System.out.println("");
                helpOrWork();
                break;
            case 4:
                System.out.println("Надо ввести название папки, "
                        + "в которой хранится файл для чтения. \n"
                        + "Если папки с таким именем не существует, "
                        + "программа предложит создать эту папку.\n"
                        + "Далее вводим имя файла для чтения. "
                        + "Если такого файла нет, программа предложит \n"
                        + "создать этот файл. Если файл существуе, "
                        + "то производится чтение из файла и вывод \n"
                        + "содержимого файла в консоль.");
                System.out.println("");
                helpOrWork();
                break;
            case 5:
                System.out.println("Надо ввести название папки, в которой "
                        + "хранится файл для вывода информации. \n"
                        + "Если папки с таким именем не существует, "
                        + "программа предложит создать эту папку.\n"
                        + "Далее вводим имя файла для вывода информации. "
                        + "Если такого файла нет, программа предложит \n"
                        + "создать этот файл. Если файл существуе, то на "
                        + "экран выведется информация о количестве \n"
                        + "строк, слов, символов в файле, дата и время "
                        + "последнего изменения файла, размер файла,");
                System.out.println("");
                helpOrWork();
                break;
            case 6:
                System.out.println("Выход из программы.");
                return;
            default:
        }
    }

    public void workCommand(int option) {
        switch (option) {
            case 1:
                fileEditor.createDirectory();
                System.out.println("");
                fileWork();
                break;
            case 2:
                fileEditor.createFile();
                System.out.println("");
                fileWork();
                break;
            case 3:
                fileEditor.writeFile();
                System.out.println("");
                fileWork();
                break;
            case 4:
                fileEditor.readFile();
                System.out.println("");
                fileWork();
                break;
            case 5:
                fileEditor.info();
                System.out.println("");
                fileWork();
                break;
            case 6:
                fileEditor.exit();
                break;
            default:
        }
    }

    public void unCorrect(int err) {
        byte[] bytes = new byte[1];
        bytes[0] = (byte) err;
        System.out.println("You enter not correct value!");
        System.out.println("Wold you like to save text?");
        System.out.println("Enter: Yes - 0, No - 1 ");
        int answer = Integer.parseInt(fileEditor.scanner());
        switch (answer) {
            case 0:
                try {
                    Files.write(Paths.get("saveFiles\\save.txt"), bytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 1:
                System.out.println("Enter correct number: ");
                break;
            default:
        }
    }
}





