package core.basesyntax;

public class Helping implements Action {

    @Override
    public void doAction(String command) {
        System.out.println("Доступные команды:" + "\n" + "/create [path] [file-name] "
                + "- создать файл по указанному пути."
                + "\n" + "\n" + "/read [path] [file-name] -"
                + " считать текст из файла и вывести его в консоль"
                + "\n" + "\n" + "/info [path] [file-name] - получить краткую информацию о файле"
                + "\n" + "\n" + "/help [command] - вывести информацию о команде"
                + "\n" + "\n" + "/exit - завершит работу программы"
                + "\n" + "\n" + "/help - повторно вывести на экран это сообщение"
                + "\n" + "\n" + "Хорошей работы!");
    }
}
