package core.basesyntax;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Reading implements Action {

    private String path;
    private String name;

    @Override
    public void doAction(String command) {
        String[] commandSplited = command.split(" ");
        if (commandSplited.length != 3) {
            throw new RuntimeException("Нужно иное количество аргументов!");
        } else {
            path = commandSplited[1].trim();
            name = commandSplited[2].trim();
        }
        try (FileInputStream stream = new FileInputStream(path + File.separator
                + name); BufferedInputStream bufStream = new BufferedInputStream(stream, 200)) {
            int i;
            System.out.println("Текст из файла:");
            while ((i = bufStream.read()) != -1) {
                System.out.print((char) i);
            }
        } catch (IOException e) {
            throw new RuntimeException("Такого файла не существует!", e);
        }
    }
}
