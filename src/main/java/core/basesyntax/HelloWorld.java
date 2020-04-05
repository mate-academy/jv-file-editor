package core.basesyntax;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class HelloWorld {
    public static void main(String[] args) {
        do {
            System.out.println(">");
            Scanner scanner = new Scanner(System.in);
            args = scanner.nextLine().split("\\s+");
            run(
                    args.length < 1 ? "" : args[0],
                    args.length < 2 ? "" : args[1],
                    args.length < 3 ? "" : args[2]
            );
        } while (true);
    }

    private static void run(String command, String param1, String param2) {
        switch (command) {
            case "":
                System.out.println("To continue enter your command and parameters: ");
                break;
            default:
                runWithCommand(command, param1, param2);
        }
    }

    private static void runWithCommand(String command, String param1, String param2) {
        switch (command) {
            case "create":
                createFile(param1, param2);
                break;
            case "read":
                readFile(param1, param2);
                break;
            case "info":
                getFileInfo(param1, param2);
                break;
            case "exit":
                printExit();
                System.exit(0);
                break;
            case "help":
                if (param1.isEmpty()) {
                    printHelp();
                } else {
                    printHelp(param1);
                }
                break;
            default:
                printUnsupportedCommand();
                break;
        }
    }

    public static void createFile(String fileDir, String fileName) {
        if (!isPresentDirAndFile(fileDir, fileName)) {
            return;
        }
        String filePath = fileDir + "/" + fileName;
        try {
            Path path = Paths.get(filePath);
            if (Files.exists(path)) {
                System.out.println("The file already exists. To overwrite existing "
                        + "file press 'y'.");
                char overwrite;
                overwrite = (char) System.in.read();
                if (overwrite == 'y') {
                    Files.delete(path);
                    Files.createFile(path);
                    System.out.println("File was overwritten: " + filePath);
                }
            } else {
                Files.createFile(path);
                System.out.println("File created: " + filePath);
            }
        } catch (Exception e) {
            printFileError(filePath);
        }
    }

    public static void readFile(String fileDir, String fileName) {
        if (!isPresentDirAndFile(fileDir, fileName)) {
            return;
        }
        String filePath = fileDir + "/" + fileName;
        try (BufferedReader reader =
                     new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException x) {
            printFileError(filePath);
        }
    }

    public static void getFileInfo(String fileDir, String fileName) {
        if (!isPresentDirAndFile(fileDir, fileName)) {
            return;
        }
        String filePath = fileDir + "/" + fileName;
        int lineCount = 0;
        int wordCount = 0;
        int charCount = 0;
        long fileSize = 0;
        FileTime lastModified = null;
        try (BufferedReader reader =
                     new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineCount++;
                System.out.println(line);
                String[] words = line.split("\\s+");
                wordCount += words.length;
                charCount += line.codePoints().toArray().length;
            }
            Path path = Paths.get(filePath);
            fileSize = Files.size(path);
            lastModified = Files.getLastModifiedTime(path);
        } catch (Exception x) {
            printFileError(filePath);
            printFileError(filePath);
        }
        System.out.println(String.join("\n", new String[] {
                "File path: " + filePath,
                "Number of lines: " + lineCount,
                "Number of words: " + wordCount,
                "Number of chars: " + charCount,
                "File size: " + fileSize,
                "Last modified: " + lastModified
        }));
    }

    private static Map<String, String> getAllCommandsHelp() {
        return new LinkedHashMap<>() {{
                put("create", String.join("\n", new String[] {
                        "- create [path] [file-name]",
                        "Creates file if the path exists and there is no such file already.",
                        " "
                }));
                put("read", String.join("\n", new String[] {
                        "- read [path] [file-name]",
                        "Reads a file and prints content to console, if the file and path exist.",
                        " "
                }));
                put("info", String.join("\n", new String[] {
                        "- info [path] [file-name]",
                        "Prints brief info about the file: number of symbols, words, lines,",
                        "date and time of last modification, size.",
                        " "
                }));
                put("help", String.join("\n", new String[] {
                        "- help [command]",
                        "Prints all available commands and information for them.",
                        " "
                }));
                put("exit", String.join("\n", new String[] {
                        "- exit",
                        "Aborts the application work.",
                        " "
                }));
            }};
    }

    public static void printHelp() {
        String help = String.join("\n",
                getAllCommandsHelp().values().toArray(new String[] {}));
        System.out.println(help);
    }

    public static void printHelp(String command) {
        Map<String, String> allCommandsHelp = getAllCommandsHelp();
        String commandHelp = allCommandsHelp.get(command);
        if (commandHelp != null) {
            System.out.println(commandHelp);
        } else {
            System.out.println("There is no description for '" + command + "' available.");
        }
    }

    public static void printExit() {
        System.out.println("Exit. That's all, folks...");
    }

    public static boolean isPresentDirAndFile(String fileDir, String fileName) {
        if (fileName.isEmpty() || fileDir.isEmpty()) {
            System.out.println("Path and/or file name are absent.");
            return false;
        }
        return true;
    }

    public static void printUnsupportedCommand() {
        System.out.println("Command not found. Do you want to save this text?");
    }

    public static void printFileError(String filePath) {
        System.out.println("Error performing operation on file: " + filePath
                + ". Please, assure file name and/or path are correct.");
    }
}
