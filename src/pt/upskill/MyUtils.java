package pt.upskill;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class MyUtils {

    public static Main.OpMainMenu getMenuOption(String menu[])
    {
        for (int i=0; i<menu.length; i++)
            System.out.printf("\t%s\n",menu[i]);
        System.out.print("\n\tOpção: ");
        Scanner scanner = new Scanner(System.in); // Create a Scanner object
        String option = scanner.next(); // Read user input
        scanner.nextLine(); // Consume \n
        return (char_To_OpMainMenu(option.substring(0,1).toUpperCase().charAt(0)));
    }

    private static Main.OpMainMenu char_To_OpMainMenu(char ch) {
        for (Main.OpMainMenu op : Main.OpMainMenu.values())
            if (op.getOption()==ch)
                return op;
        return null;
    }


    public static void readFile(String csvFile){


        try ( Scanner scanner = new Scanner(new File(csvFile))) {
            scanner.useDelimiter(";");
            while (scanner.hasNext()) {
                String data = scanner.next();
                System.out.println(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {

        }
    }


}
