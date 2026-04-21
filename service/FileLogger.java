package service;

import java.io.FileWriter;
import java.io.IOException;

public class FileLogger {

    public static void log(String fileName, String data) {
        try {
            FileWriter fw = new FileWriter(fileName, true);
            fw.write(data + "\n");
            fw.close();

            System.out.println("LOG → " + fileName); // debug
        } catch (IOException e) {
            System.out.println("Error writing to file");
        }
    }
}