package core.basesyntax;

import java.util.Scanner;

import static core.basesyntax.Methods.*;

public class MainApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Write your request in format 'command [path] [filename]': ");
        String request = scanner.nextLine();

        String[] requestArray = request.split(" ");
        String command = requestArray[0].toLowerCase();
        String path = (requestArray.length == 2) ?
                requestArray[1].substring(1, requestArray[1].length() - 1) : null;
        String filename = (requestArray.length == 3) ?
                requestArray[2].toLowerCase().substring(1, requestArray[2].length() - 1) : null;

        switch (command) {
            case "help":
                if (requestArray.length > 1) {
                    helpByCommand(path);
                } else {
                    help();
                }
                break;
            case "create":
                createFile(path, filename);
                break;
            case "read":
                readFile(path, filename);
                break;
            case "info":
                fileInfo(path, filename);
                break;
            case "exit":
                break;
            default:
                saveText();
            }
        }
    }
