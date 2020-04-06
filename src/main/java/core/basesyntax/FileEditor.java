package core.basesyntax;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Paths.get;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileTime;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class FileEditor {
    Path directory;
    Path pathFile;
    Path file;
    Path pathDirectory;

    public String scanner() {
        Scanner scanner = new Scanner(System.in);
        String input = "";
        if (scanner.hasNextLine()) {
            input = scanner.nextLine();
        }
        return input;
    }

    //Создали дирректорию
    public void createDirectory() {
        System.out.println("Enter a name of directory: ");
        pathDirectory = get(scanner());
        try {
            directory = Files.createDirectory(pathDirectory);
            System.out.println(Files.exists(pathDirectory)
                    ? "Directory exist!" : "Directory doesn't exist!");
        } catch (IOException e) {
            System.out.println("The directory with this name exist, please, enter another name: ");
            createDirectory();
        }
    }

    //Создали файл
    public void createFile() {
        System.out.println("Enter a name of directory, in which you want to write file: ");
        String nameDirectory = scanner();
        if (Files.exists(get(nameDirectory))) {
            System.out.println("Enter a name of file: ");
            String nameFile = scanner();
            pathFile = get(nameDirectory + "\\" + nameFile + ".txt");
            try {
                file = Files.createFile(pathFile);
            } catch (IOException e) {
                System.out.println("File with this name exist!");
                System.out.println("Pleas enter 1 - rewrite file, "
                        + "2 - clean file, 3 - create new file.");
                int input = Integer.parseInt(scanner());
                switch (input) {
                    case 1:
                        System.out.println("Enter name of copy file:");
                        try {
                            Files.copy(get(nameDirectory + "\\" + nameFile + ".txt"),
                                    get(nameDirectory + "\\" + scanner() + ".txt"),
                                    StandardCopyOption.REPLACE_EXISTING);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        break;
                    case 2:
                        System.out.println("File " + nameFile + " will clean!");
                        try {
                            Files.write(get(nameDirectory + "\\" + nameFile + ".txt"),
                                    Collections.singleton(""));
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        break;
                    case 3:
                        createFile();
                        break;
                    default:
                }
                System.out.println("File create!");
            }
        } else {
            System.out.println("Directory not found! Please, create directory: ");
            createDirectory();
        }
    }

    //Записали в файл
    public void writeFile() {
        System.out.println("Enter the directory of writing file: ");
        String nameDirectory = scanner();
        if (Files.exists(get(nameDirectory))) {
            System.out.println("Enter the name of writing file: ");
            String nameFile = scanner();
            if (Files.exists(get(nameDirectory + "\\" + nameFile + ".txt"))) {
                try {
                    System.out.println("Please, enter text: ");
                    Files.write(get(nameDirectory
                            + "\\" + nameFile + ".txt"), scanner().getBytes());
                } catch (IOException e) {
                    System.out.println("File not found! Please, create file: ");
                    createFile();
                }
            } else {
                System.out.println("Directory not found! Please, create directory: ");
                createDirectory();
            }
        }
    }

    //чтение файла
    public void readFile() {
        List<String> lines = null;
        System.out.println("Enter the directory of reading file: ");
        String nameDirectory = scanner();
        if (Files.exists(get(nameDirectory))) {
            System.out.println("Enter the name of reading file: ");
            String nameFile = scanner();
            if (Files.exists(get(nameDirectory + "\\" + nameFile + ".txt"))) {
                try {
                    lines = Files.readAllLines(get(
                            nameDirectory + "\\" + nameFile + ".txt"), UTF_8);
                } catch (IOException e) {
                    System.out.println("File not found!");
                    createFile();
                }
                for (String s : lines) {
                    System.out.println(s);
                }
            }
        } else {
            System.out.println("Directory not found! Please, create directory: ");
            createDirectory();
        }
    }

    public void info() {
        List<String> string = null;
        int count = 0;
        //количество символов
        System.out.println("Enter the directory of file get info: ");
        String nameDirectory = scanner();
        if (Files.exists(get(nameDirectory))) {
            System.out.println("Enter name of file get info: ");
            String nameFile = scanner();
            if (Files.exists(get(nameDirectory + "\\" + nameFile + ".txt"))) {
                //количество строк
                try {
                    string = Files.readAllLines(get(nameDirectory + "\\" + nameFile + ".txt"));
                    System.out.println("How much lines: " + string.size());
                } catch (IOException e) {
                    System.out.println("File not found! Please, create file: ");
                    createFile();
                }
                // количество слов
                for (String str : string) {
                    if (string.size() != 0) {
                        count++;
                        for (int i = 0; i < str.length(); i++) {
                            if (str.charAt(i) == ' ') {
                                count++;
                            }
                        }
                    }
                }
                //количество символов
                try {
                    byte[] elements = Files.readAllBytes(
                            get(nameDirectory + "\\" + nameFile + ".txt"));
                    System.out.println("How much characters in the list: "
                            + (elements.length + 1 - string.size()));
                } catch (IOException e) {
                    System.out.println("File not found! Please, create file: ");
                    createFile();
                }
                System.out.println("How much words: " + count);
                //дата последнего изменения файла
                try {
                    FileTime data = Files.getLastModifiedTime(
                            get(nameDirectory + "\\" + nameFile + ".txt"));
                    System.out.println("Data last changes is: " + data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //размер файла
                try {
                    long size = Files.size(get(nameDirectory + "\\" + nameFile + ".txt"));
                    System.out.println("Size of file: " + size + " bytes");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Directory not found! Please, create directory: ");
            createDirectory();
        }
    }

    public int exit() {
        System.out.println("Good bye!");
        return 0;
    }
}

