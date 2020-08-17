package core.basesyntax;

import java.util.Scanner;

public class Commands {
    public static final String GREETING = "Доброго времени суток!" + "\n"
            + "Вы используете приложение для работы с файлами."
            + "\n" + "Доступные команды:" + "\n" + "/create [path] [file-name] "
            + "- создать файл по указанному пути." + "\n" + "\n" + "/read [path] [file-name] -"
            + " считать текст из файла и вывести его в консоль"
            + "\n" + "\n" + "/info [path] [file-name] - получить краткую информацию о файле"
            + "\n" + "\n" + "/help [command] - вывести информацию о команде"
            + "\n" + "\n" + "/exit - завершит работу программы"
            + "\n" + "\n" + "/help - повторно вывести на экран это сообщение"
            + "\n" + "\n" + "Хорошей работы!";
    private String command;
    private Scanner scanner;

    public void appStart() {
        System.out.println(GREETING);
        scanner = new Scanner(System.in);
        appListening();
    }

    public void appListening() {
        command = scanner.nextLine();
        try {
            commandManager();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            appListening();
        }
    }

    public void commandManager() {
        Action action = command.equals("/help") ? new Helping()
                : (command.contains("/help") ? new CommandHelping()
                : (command.contains("/info") ? new Information()
                : ((command.contains("/read")) ? new Reading()
                : (command.contains("/create") ? new Creating()
                : (command.contains("/exit") ? new Exiting() : new DataExecuting())))));
        action.doAction(command);
        appListening();
    }
}
