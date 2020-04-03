package core.basesyntax;

import java.io.IOException;
import java.util.Scanner;

public class FileEditor {

    public void start() {
        Scanner scanner = new Scanner(System.in);
        Command command = new Command(scanner);
        try {
            boolean bool = true;
            while (bool) {
                scanner = new Scanner(System.in);
                System.out.println("Write command or text: ");
                String[] input = scanner.nextLine().split(" ");
                String path = input.length > 1 ? input[1] : "";
                String fileName = input.length > 2 ? input[2] : "";
                switch (getCommand(input)) {
                    case 0:
                        System.out.println("Your input is empty!");
                        break;
                    case 1:
                        command.createFile(path, fileName);
                        break;
                    case 2:
                        command.readFile(path, fileName);
                        break;
                    case 3:
                        command.getInfo(path, fileName);
                        break;
                    case 4:
                        command.callHelp(path);
                        break;
                    case 5:
                        command.callHelp();
                        break;
                    case 6:
                        bool = false;
                        break;
                    default:
                        command.saveText(input);
                        break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            scanner.close();
        }
        System.out.println("Program is finished!");
    }

    private static int getCommand(String[] input) {
        int length = input.length;
        return String.join("", input).length() == 0
                ? 0 : input[0].startsWith("create") && length == 3
                ? 1 : input[0].startsWith("read") && length == 3
                ? 2 : input[0].startsWith("info") && length == 3
                ? 3 : input[0].startsWith("help") && length == 2
                ? 4 : input[0].startsWith("help") && length == 1
                ? 5 : input[0].startsWith("exit") && length == 1
                ? 6 : 7;
    }
}
