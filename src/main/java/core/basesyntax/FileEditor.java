package core.basesyntax;

import java.util.HashMap;
import java.util.Scanner;

public class FileEditor {
    private Scanner scanner = new Scanner(System.in);
    private FileCreator fileCreator = new FileCreator();
    private FileReader fileReader = new FileReader();

    public void start() {
        boolean work = true;
        infoMessage();
        while (work) {
            String[] variant = scanner.nextLine().split(" ");
            checkInputLength(variant.length);
            switch (variant[0].toLowerCase()) {
                case "create":
                    if (variant.length != 3) {
                        break;
                    }
                    System.out.print(fileCreator.create(variant[1], variant[2]));
                    break;
                case "read":
                    if (variant.length != 3) {
                        break;
                    }
                    System.out.print(fileReader.read(variant[1], variant[2]));
                    break;
                case "info":
                    if (variant.length != 3) {
                        break;
                    }
                    System.out.print(fileReader.info(variant[1], variant[2]));
                    break;
                case "help":
                    if (variant.length == 2) {
                        System.out.println(help(variant[1]));
                        break;
                    }
                    help();
                    break;
                case "exit":
                    work = false;
                    break;
                default:
                    wantToSave();
            }
        }
    }

    private void checkInputLength(int inputLength) {
        if (inputLength > 3 || inputLength < 1) {
            infoMessage();
        }
    }

    private void infoMessage() {
        System.out.println("Please enter valid command with correct amount parameters.\n"
                + "You can get list of available commands by entering 'help'.\n>");
    }

    private void help() {
        System.out.println("create [path] [filename] - creates new file at path\n"
                + "read [path] [filename] - reads file at path\n"
                + "info [path] [filename] - shows info on file at path\n"
                + "help - shows all commands\n"
                + "help [command] - shows more detailed info on the command\n"
                + "exit - exits program\n");
    }

    private String help(String command) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("create", "create [path] [filename] - creates new file at path\n"
                + "Creates new file with provided filename at provided path.\n"
                + "[path] - should have correct delimeters:\n"
                + "/ - for Linux/Mac\n\\ - for Windows\n"
                + "If file already exists you will be prompted "
                + "whether you want to overwrite it\n");
        hashMap.put("read", "read [path] [filename] - reads file at path\n"
                + "Outputs content of file in console.\n"
                + "[path] - should have correct delimeters:\n"
                + "/ - for Linux/Mac\n\\ - for Windows\n");
        hashMap.put("info", "info [path] [filename] - shows info on file at path\n"
                + "Outputs information on provided file like"
                + "number of characters, lines, words, date "
                + "and time of last modification and filesize.\n"
                + "[path] - should have correct delimeters:\n"
                + "/ - for Linux/Mac\n\\ - for Windows\n");
        hashMap.put("help", "help - shows all commands\n"
                + "If entered with second argument and second argument is command name"
                + " like 'help create' it will provide more detailed info on the command.\n");
        hashMap.put("exit", "Exits program.\n");

        String result = hashMap.get(command);
        return result != null ? result : "Malformed request. Please try again\n";
    }

    private void wantToSave() {
        System.out.println("Do you want to save text? [y/n]\n");
        switch (scanner.nextLine().toLowerCase()) {
            case "y":
                System.out.println("Please enter path to file and filename separated with space\n");
                String[] input = scanner.nextLine().split(" ");
                if (input.length == 2) {
                    fileCreator.create(input[0], input[1]);
                    break;
                }
                System.out.println("Please enter path to file and its name\n");
                break;
            case "n":
                System.out.println("Ok.\n");
                break;
            default:
                System.out.println("Please try again\n");
        }

    }
}
