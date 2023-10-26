package pt.upskill;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileRead {

    File f;
    FileReader file;
    BufferedReader input = null;
    public void init(String name) {
        try {
            f = new File(name);
            file = new FileReader(f);
            input = new BufferedReader(file);
        } catch (IOException e) {
            System.out.println("I/O Error");
        }
    }

    public boolean exists(String name) {
        File f;

        f = new File(name);   //associa f com um ficheiro
        return f.exists();
    }

    public void close() {
        if (input != null) {
            try {
                input.close();
            } catch (IOException e) {
                System.out.println("Error closing file");
            }
        }
    }

    public String readLine() {
        try {
            return input.readLine();
        } catch (IOException e) {
            System.out.println("Error reading file!");
            return null;
        }
    }
}
