package pt.upskill;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

public class MyUtils {

    public static Main.OpMainMenu getMenuOption(String[] menu)
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




    public static void loadIdDescFromFile(ListIdDesc list, String fileName)
    {

        FileRead fileRead = new FileRead();
        fileRead.init(fileName);
        String line;
        IdDesc idDesc;
        StringTokenizer st;

        do{
            line=fileRead.readLine();
            if (line != null){
                st = new StringTokenizer(line, ";");
                idDesc = new IdDesc( Integer.parseInt(st.nextToken().trim()), st.nextToken().trim());
                list.add(idDesc);
            }
        } while (line!=null);

        fileRead.close();
    }

}
