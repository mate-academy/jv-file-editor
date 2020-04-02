package core.basesyntax;

import java.util.Scanner;

/**
 * Feel free to remove this class and create your own.
 */
public class HelloWorld {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Write your request in format 'command [path] [filename]': ");
        String request = scanner.nextLine();
        String[] requestArray = request.split(" ");
        if (requestArray[0].toLowerCase().equals("help")) {
            if (requestArray.length > 1){
                helpByCommand();
            } else {
                help();
            }
        } else {
            switch (requestArray[0].toLowerCase()) {
                case "create":
                    createFile();
                    break;
                case "read":
                    readFile();
                    break;
                case "info":
                    fileInfo();
                    break;
                case "exit":
                    break;
                default:
                    saveText();
            }
        }
    }

    private static void createFile() {
        System.out.println("File created.");
    }

    private static void readFile() {
        String result = "";
        System.out.println(result);
    }

    private static void fileInfo() {
        System.out.println();
    }

    private static void help() {

    }

    private static void helpByCommand() {

    }

    private static void saveText() {
        System.out.println("Would you like to save your text? (y/n): ");

    }
}
