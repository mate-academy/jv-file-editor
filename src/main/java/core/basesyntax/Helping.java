package core.basesyntax;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Helping {

    public static void comands(String comad) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("help.txt"));
        if (comad.equals("help")) {
            for (String s : lines) {
                System.out.println(s);
            }
        }
        if (comad.equals("help [create]")) {
            System.out.println(lines.get(0));
        } else if (comad.equals("help [read]")) {
            System.out.println(lines.get(1));
        } else if (comad.equals("help [info]")) {
            System.out.println(lines.get(2));
        } else if (comad.equals("help [help]")) {
            System.out.println(lines.get(3));
        } else if (comad.equals("help [command]")) {
            System.out.println(lines.get(4));
        } else if (comad.equals("help [exit]")) {
            System.out.println(lines.get(5));
        }
    }
}
