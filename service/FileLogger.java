package service;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileLogger {

    public static void log(String fileName, String data) {
        try {
            FileWriter fw = new FileWriter(fileName, true);

            // 🔥 formatted readable time
            LocalDateTime now = LocalDateTime.now();

            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a");

            String time = now.format(formatter);

            // 🔥 final log format
            fw.write("[" + time + "] " + data + "\n");

            fw.close();

        } catch (IOException e) {
            System.out.println("Error writing to file");
        }
    }
}