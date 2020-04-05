package core.basesyntax;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainApp {

    public static final String REGEX2WORD = "^\\w{4}\\s\\[\\w{4,6}]$";
    public static final String REGEX3WORD = "^\\w{4,6}\\s\\[.+]\\s\\[.+]$";

    public static void main(String[] args) {
        Scanner scanner;
        boolean finalDecision = true;
        do {
            scanner = new Scanner(System.in);
            System.out.print("\nWrite your request in next format "
                    + "'command [/path/.../] [filename.extension]' "
                    + "or write 'help' to see all available commands: ");
            String request = scanner.nextLine();

            Pattern pattern2Word = Pattern.compile(REGEX2WORD);
            Pattern pattern3Word = Pattern.compile(REGEX3WORD);
            Matcher matcher2Word = pattern2Word.matcher(request);
            Matcher matcher3Word = pattern3Word.matcher(request);

            if (matcher3Word.matches()) {
                String[] requestArray = request.split(" ");
                String command = requestArray[0].toLowerCase();
                String path = requestArray[1].substring(1, requestArray[1].length() - 1);
                String filename = requestArray[2].substring(1, requestArray[2].length() - 1);

                switch (command) {
                    case "create":
                        Methods.createFile(path, filename);
                        break;
                    case "read":
                        try {
                            Methods.readFile(path, filename);
                        } catch (IOException e) {
                            System.out.println("No file/directory found!");
                        }
                        break;
                    case "info":
                        try {
                            Methods.fileInfo(path, filename);
                        } catch (IOException e) {
                            System.out.println("No file/directory found!");
                        }
                        break;
                    default:
                        Methods.saveText(command);
                }
            } else if (matcher2Word.matches() && request.split(" ")[0]
                    .toLowerCase().contentEquals("help")) {
                Methods.helpByCommand(request.split(" ")[1]
                        .substring(1, request.split(" ")[1].length() - 1));
            } else {
                switch (request) {
                    case "help":
                        Methods.help();
                        break;
                    case "exit":
                        finalDecision = false;
                        break;
                    default:
                        Methods.saveText(request);
                }
            }

            if (!request.toLowerCase().contentEquals("exit")) {
                boolean localDecision = true;
                do {
                    scanner = new Scanner(System.in);
                    System.out.print("\nWould you like to do anything else? (y/n): ");
                    String answer = scanner.nextLine();
                    if (answer.contentEquals("n")) {
                        localDecision = false;
                        finalDecision = false;
                    } else if (answer.contentEquals("y")) {
                        localDecision = false;
                    } else {
                        System.out.println("Wrong input, try again!");
                    }
                } while (localDecision);
            }
        } while (finalDecision);
        System.out.println("Program is ending...");
        scanner.close();
    }
}
