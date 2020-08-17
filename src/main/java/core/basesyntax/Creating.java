package core.basesyntax;

import java.io.File;
import java.io.IOException;

public class Creating implements Action {

    private String path;
    private String name;

    @Override
    public void doAction(String command) {
        String[] commandData = command.split(" ");
        if (commandData.length != 3) {
            throw new RuntimeException("Нужно иное количество аргументов!");
        } else {
            path = commandData[1].trim();
            name = commandData[2].trim();
        }
        try {
            File file = new File(path + File.separator + name);
            file.createNewFile();
            System.out.println("Файл успешно создан!");
        } catch (IOException e) {
            throw new RuntimeException("Такого пути не существует!", e);
        }
    }
}
