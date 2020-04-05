package core.basesyntax;

import java.util.Scanner;

public class FileBuilder {

    public static void start() {
        System.out.println("Hi i am FileBuilder ");
        boolean answerExist = false;
        while (!answerExist) {
            System.out.println("Use 'help' for more information"
                    + "\n" + "Write here your command:");
            Scanner a = new Scanner(System.in);
            String input = a.nextLine();
            answerExist = true;
            switch (input) {
                case "help":
                    Console.help();
                    answerExist = false;
                    break;
                case "create":
                    Console.create();
                    answerExist = false;
                    break;
                case "read":
                    Console.read();
                    answerExist = false;
                    break;
                case "info":
                    Console.info();
                    answerExist = false;
                    break;
                case "exit":
                    System.out.println("Exiting..." + "\n" + "DONE!");
                    break;
                default:
                    if (input.endsWith(")")) {
                        Console.help(input.substring(input.indexOf("(")
                                + 1,input.lastIndexOf(")")));
                        answerExist = false;
                        break;
                    } else {
                        System.out.println("COMMAND NOT FOUND!" + "\n"
                                + "Do you want save this text:" + "--> "
                                + input + " 'yes' or 'no'?");
                        String input1 = a.nextLine();
                        boolean yorn = false;
                        while (!yorn) {
                            switch (input1.toLowerCase()) {
                                case "yes":
                                    Console.write(input);
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
                                    input1 = a.nextLine();
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
