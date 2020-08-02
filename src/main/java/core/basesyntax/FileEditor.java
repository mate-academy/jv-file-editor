package core.basesyntax;

import java.nio.file.Path;
import java.util.Scanner;

public class FileEditor {
    private Scanner scanner = new Scanner(System.in);
    private boolean work = true;

    public void start() {
        System.out.println("Hello! Please enter the command.\n"
                + "You can get list of available commands by entering 'help'.\n");
        while (work) {
            String[] variant = scanner.nextLine().split(" ");
            if (variant.length > 3) {
                System.out.println("Please enter valid command with correct amount parameters.\n"
                        + "You can get list of available commands by entering 'help'.\n");
            } else {
                switch (variant[0].toLowerCase()) {
                    case "create":
                        //create(variant[1], variant[2]);
                        break;
                    case "read":
                        //read(variant[1], variant[2]);
                    case "info":
                        //info(variant[1], variant[2]);
                        break;
                    case "help":
                        if (variant.length == 2) {
                            help(variant[1]);
                        } else {
                            help();
                        }
                        break;
                    case "exit":
                        exit();
                        break;
                    default:
                        wantToSave();
                }
            }

        }
    }

    private void create(Path path, String filename) {

    }

    private void read(Path path, String filename) {

    }

    private void info(Path path, String filename) {

    }

    private void help() {
        System.out.println("create [path] [filename] - creates new file at path\n"
                + "read [path] [filename] - reads file at path\n"
                + "info [path] [filename] - shows info on file at path\n"
                + "help - shows all commands\n"
                + "help [command] - shows more detailed info on the command\n"
                + "exit - exits program");
    }

    private void help(String command) {
        switch (command) {
            case "create":
                System.out.println("create [path] [filename] - creates new file at path\n"
                        + "Creates new file with provided filename at provided path.\n"
                        + "[path] - should have correct delimeters:\n"
                        + "/ - for Linux/Mac\n\\ - for Windows\n"
                        + "If file already exists you will be prompted "
                        + "whether you want to overwrite it");
                break;
            case "read":
                System.out.println("read [path] [filename] - reads file at path\n"
                        + "Outputs content of file in console.\n"
                        + "[path] - should have correct delimeters:\n"
                        + "/ - for Linux/Mac\n\\ - for Windows");
                break;
            case "info":
                System.out.println("info [path] [filename] - shows info on file at path\n"
                        + "Outputs information on provided file like"
                        + "number of characters, lines, words, date "
                        + "and time of last modification and filesize.\n"
                        + "[path] - should have correct delimeters:\n"
                        + "/ - for Linux/Mac\n\\ - for Windows");
                break;
            case "help":
                System.out.println("help - shows all commands\n"
                        + "If entered with second argument and second argument is command name"
                        + " like 'help create' it will provide more detailed info on the command.");
                break;
            case "exit":
                System.out.println("Exits program.");
                break;
            default:
                System.out.println("Malformed request. Please try again");
        }
    }

    private void exit() {
        System.out.println("Goodbye!");
        work = false;
    }

    private void wantToSave() {
        System.out.println("Do you want to save text? [y/n]");
        switch (scanner.nextLine().toLowerCase()) {
            case "y":
                System.out.println("Please enter path to file and filename separated with space");
                String[] input = scanner.nextLine().split(" ");
                //create(input[0], input[1]);
                break;
            case "n":
                System.out.println("Ok.");
                break;
            default:
                System.out.println("Please try again");
        }

    }
}
