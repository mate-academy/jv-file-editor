package core.basesyntax;

public class CommandHelping implements Action {
    @Override
    public void doAction(String command) {
        command = command.replace("/help ", "");
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
}
