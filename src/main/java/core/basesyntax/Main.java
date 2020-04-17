package core.basesyntax;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.print("$ ");
        Console.handle(new Scanner(System.in));
    }
}
