package core.basesyntax;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DataExecuting implements Action {
    @Override
    public void doAction(String command) {
        String[] commandSplited = command.split(" ");
        if (commandSplited.length == 2 && commandSplited[0] == "path") {
            File file = new File("text.txt");
            file.renameTo(new File(commandSplited[1] + file.getName()));
            System.out.println("Файл создан");
        }
        switch (commandSplited[0]) {
            case "Да":
                System.out.println("Файл создан");
                break;
            case "Нет":
                System.out.println("Введите адрес записи в формате: path [directory]");
                break;
            default:
                File file = new File("text.txt");
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    throw new RuntimeException("Такого пути не существует!", e);
                }
                try (FileOutputStream stream = new FileOutputStream("text.txt", true);) {
                    stream.write(command.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException("Такого файла не существует!", e);
                }
                System.out.println("Записать текст в новый файл в директории программы? Да Нет");
                break;
        }
    }
}
