package core.basesyntax;

import java.util.Date;

public interface Texts {

    default void greeting() {
        System.out.println("Доброго времени суток!" + "\n"
                + "Вы используете приложение для работы с файлами."
                + "\n" + "Доступные команды:" + "\n"
                + "/create [path] [file-name] - создать файл по указанному пути."
                + "\n" + "\n"
                + "/read [path] [file-name] - считать текст из файла и вывести его в консоль"
                + "\n" + "\n" + "/info [path] [file-name] - получить краткую информацию о файле"
                + "\n" + "\n" + "/help [command] - вывести информацию о команде"
                + "\n" + "\n" + "/exit - завершит работу программы"
                + "\n" + "\n" + "/help - повторно вывести на экран это сообщение"
                + "\n" + "\n" + "Хорошей работы!" + "\n");
    }

    default void commandsHelp(String command) {
        switch (command) {
            case "/create":
                System.out.println("/create [path] [file-name] "
                        + "- создать файл по указанному пути.");
                break;
            case "/read":
                System.out.println("/read [path] [file-name] -"
                        + " считать текст из файла и вывести его в консоль");
                break;
            case "/info":
                System.out.println("/info [path] [file-name] -"
                        + " получить краткую информацию о файле");
                break;
            case "/help":
                System.out.println("/help [command] - вывести информацию о команде." + "\n"
                        + "/help - вывести на экран помощь по всем командам.");
                break;
            case "/exit":
                System.out.println("/exit - завершить работу программы");
                break;
            default:
                System.out.println("Такой команды не существует");
                break;
        }
    }

    default void info(int symbolsCount, int linesCount, int wordsCount,
                      Date lastModified, double size) {
        System.out.println("Информация о файле" + "\n"
                + "Количество символов: " + symbolsCount + "\n"
                + "Количество строк: " + linesCount + "\n" + "Количество слов: "
                + wordsCount + "\n" + "Файл изменялся последний раз: "
                + lastModified + "\n" + "Размер файла: " + size);
    }

    default void help() {
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
