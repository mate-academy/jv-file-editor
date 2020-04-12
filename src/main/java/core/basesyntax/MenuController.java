package core.basesyntax;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import static core.basesyntax.CreateFile.createFile;
import static core.basesyntax.Info.getInfoAboutFile;
import static core.basesyntax.ReadFile.readFiles;

public class MenuController {

    private static String obtainCurrentDateString() {
        String pattern = "dd.MM";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        return date;
    }

    public static String todayDate = obtainCurrentDateString();

    public static boolean processUserSelection(String chosenItem) throws IOException {
        boolean isExitChosen = false;
        switch (chosenItem) {
            case "1":
                System.out.println("You chose: Create new files");
                createFile();
                break;
            case "2":
                System.out.println("You chose: Read file");
                readFiles();
                break;
            case "3":
                System.out.println("You chose: Info about file");
                getInfoAboutFile();
                break;
            case "4":
                System.out.println("You chose: helpCommand");
                break;
            case "5":
                System.out.println("You chose: Exit ");
                isExitChosen = true;
                break;

            default:
                System.out.println("Error");
                System.out.println("Save these changes?");

        }
        return isExitChosen;
    }
}
