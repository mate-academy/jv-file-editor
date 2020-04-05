package core.basesyntax;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class FileEditor {
    private Scanner scanner = new Scanner(System.in);
    private Command command = new Command();

    public void start() throws IOException {
        while (true) {
            System.out.println("Примечание, я умею "
                    + "работать только с .txt файлами.");
            System.out.println("Напишите комманду или текст: ");
            String temp = scanner.nextLine();
            String[] input = temp.split(" ");
            String path = input.length > 1 ? input[1] : "";
            String fileName = input.length > 2 ? input[2] : "";
            switch (getCommand(input)) {
                case 0:
                    System.out.println("Вы ничего не ввели.\n"
                            + "Воспользуйтесь командой help");
                    break;
                case 1:
                    command.create(path, fileName);
                    break;
                case 2:
                    command.read(path, fileName);
                    break;
                case 3:
                    command.info(path, fileName);
                    break;
                case 4:
                    command.help(path);
                    break;
                case 5:
                    command.help();
                    break;
                case 6:
                    command.exit();
                    break;
                default:
                    command.save(Arrays.toString(input));
                    break;
            }
        }
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
