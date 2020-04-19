package core.basesyntax;

import java.util.Scanner;

public class FileBuilder {
    public static void main(String[] args) {
        FileBuilder a = new FileBuilder();
        a.start();
    }

    public void start() {
        Scanner scan = new Scanner(System.in);
        Console console = new Console();
        System.out.println("Hi i am FileBuilder ");
        boolean answerExist = false;
        while (!answerExist) {
            System.out.println("Use 'help' for more information"
                    + "\n" + "Write here your command:");
            String input = scan.nextLine();
            answerExist = true;
            switch (input) {
                case "help":
                    console.help();
                    answerExist = false;
                    break;
                case "create":
                    console.create(console.pathName());
                    answerExist = false;
                    break;
                case "read":
                    String[] a = console.pathName();
                    console.create(a);
                    console.read(a[0], a[1]);
                    answerExist = false;
                    break;
                case "info":
                    console.info(console.pathName());
                    answerExist = false;
                    break;
                case "exit":
                    System.out.println("Exiting..." + "\n" + "DONE!");
                    break;
                default:
                    if (input.endsWith(")")) {
                        console.help(input.substring(input.indexOf("(")
                                + 1,input.lastIndexOf(")")));
                        answerExist = false;
                        break;
                    } else {
                        System.out.println("COMMAND NOT FOUND!" + "\n"
                                + "Do you want save this text:" + "--> "
                                + input + " 'yes' or 'no'?");
                        String input1 = scan.nextLine();
                        boolean yorn = false;
                        while (!yorn) {
                            switch (input1.toLowerCase()) {
                                case "yes":
                                    console.write(console.pathName(),input);
                                    yorn = true;
                                    break;
                                case ("no"):
                                    yorn = true;
                                    break;
                                case ("exit"):
                                    yorn = true;
                                    break;
                                default:
                                    System.out.println("'yes' OR 'no' format pls.");
                                    input1 = scan.nextLine();
                                    yorn = false;
                                    break;
                            }
                        }
                        answerExist = false;
                        break;
                    }
            }
        }
    }
}
