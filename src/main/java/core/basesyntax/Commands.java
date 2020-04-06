package core.basesyntax;

import java.io.IOException;
import java.util.Scanner;

public class Commands {

    private static final String CREATE_HELP = "Name:\n"
            + "\t create - create file\n\n"
            + "SYNOPTIC\n"
            + "\t crate [path] [file name]";

    private static final String READ_HELP = "Name:\n"
            + "\t read - read file\n\n"
            + "SYNOPTIC\n"
            + "\t read [path] [file name]";

    private static final String INFO_HELP = "Name:\n"
            + "\t info - Displays brief information on the specified file:\n"
            + "number of characters, lines, words, date and lime of last change, file size\n\n"
            + "SYNOPTIC\n"
            + "\t info [path] [file name]";

    private static final String HELP_HELP = "Name:\n"
            + "\t help - Displays information on the specified command to the console\n\n"
            + "SYNOPTIC\n"
            + "\t help\n"
            + "\t help [command]";
    private static final String EXIT_HELP = "Name:\n"
            + "\t exit - close program.";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] arguments;

        while (true) {
            arguments = scanner.nextLine().split(" ");

            switch (arguments[0]) {
                case "create":
                    if (arguments.length != 3) {
                        System.out.println("Wrong number of arguments.");
                        help("create");
                        break;
                    }
                    try {
                        FileEditor.create(arguments[1], arguments[2]);
                    } catch (IOException e) {
                        throw new RuntimeException("Not created file. Error: ", e);
                    }
                    break;
                case "read":
                    if (arguments.length != 3) {
                        System.out.println("Wrong number of arguments.");
                        help("read");
                        break;
                    }
                    try {
                        FileEditor.read(arguments[1], arguments[2]);
                    } catch (IOException e) {
                        throw new RuntimeException("Can't read file. Error: ", e);
                    }
                    break;
                case "info":
                    if (arguments.length != 3) {
                        System.out.println("Wrong number of arguments.");
                        help("info");
                        break;
                    }
                    try {
                        FileEditor.info(arguments[1], arguments[2]);
                    } catch (IOException e) {
                        throw new RuntimeException("Can't read file. Error: ", e);
                    }
                    break;
                case "help":
                    switch (arguments.length) {
                        case 1:
                            help();
                            break;
                        case 2 :
                            help(arguments[1]);
                            break;
                        default:
                            System.out.println("Wrong number of arguments.");
                            help("help");
                            break;
                    }
                    break;
                case "exit":
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    break;
            }
        }
    }

    private static void help() {
        System.out.println(CREATE_HELP);
        System.out.println(READ_HELP);
        System.out.println(INFO_HELP);
        System.out.println(HELP_HELP);
        System.out.println(EXIT_HELP);
    }

    private static void help(String command) {
        switch (command) {
            case "create":
                System.out.println(CREATE_HELP);
                break;
            case "read" :
                System.out.println(READ_HELP);
                break;
            case "info" :
                System.out.println(INFO_HELP);
                break;
            case "help" :
                System.out.println(HELP_HELP);
                break;
            case "exit" :
                System.out.println(EXIT_HELP);
                break;
            default: break;
        }
    }
}
