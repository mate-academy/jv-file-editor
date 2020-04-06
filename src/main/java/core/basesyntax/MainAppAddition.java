package core.basesyntax;

import org.w3c.dom.ls.LSOutput;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainAppAddition {

    private static boolean finalDecision = true;

    public void mainApp() {
        final String REGEX2WORD = "^\\w{4}\\s\\[\\w{4,6}]$";
        final String REGEX3WORD = "^\\w{4,6}\\s\\[.+]\\s\\[.+]$";
        MainAppAddition app = new MainAppAddition();
        Scanner scanner;
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
                app.methodOf3Words(request);
            } else if (matcher2Word.matches() && request.split(" ")[0]
                    .toLowerCase().contentEquals("help")) {
                app.methodOf2Words(request);
            } else {
                app.defaultMethod(request);
            }

            if (request.toLowerCase().contentEquals("exit")) {
                finalDecision = false;
            } else {
                app.anythingElseMethod();
            }
        } while (finalDecision);
        scanner.close();
    }

    public void methodOf3Words(String request) {
        Methods method = new Methods();
        String[] requestArray = request.split(" ");
        String command = requestArray[0].toLowerCase();
        Path path = Paths.get(requestArray[1].substring(1, requestArray[1].length() - 1),
                requestArray[2].substring(1, requestArray[2].length() - 1));

        switch (command) {
            case "create":
                method.createFile(path);
                break;
            case "read":
                try {
                    method.readFile(path);
                } catch (IOException e) {
                    System.out.println("No file/directory found!");
                }
                break;
            case "info":
                try {
                    method.fileInfo(path);
                } catch (IOException e) {
                    System.out.println("No file/directory found!");
                }
                break;
            default:
                method.saveText(command);
        }
    }

    public void methodOf2Words(String request) {
        Methods method = new Methods();
        method.helpByCommand(request.split(" ")[1]
                .substring(1, request.split(" ")[1].length() - 1));
    }

    public void defaultMethod(String request) {
        Methods method = new Methods();
        switch (request) {
            case "help":
                method.help();
                break;
            case "exit":
                finalDecision = false;
                break;
            default:
                method.saveText(request);
        }
    }

    public void anythingElseMethod() {
        boolean localDecision = true;
        do {
            Scanner scanner = new Scanner(System.in);
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
}
