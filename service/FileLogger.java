package service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileLogger {

    public static void log(String fileName, String data) {
        try {
            FileWriter fw = new FileWriter(fileName, true);

            // 🔥 current time
            LocalDateTime now = LocalDateTime.now();

            // 🔥 format
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            String timestamp = "[" + now.format(formatter) + "] ";

            fw.write(timestamp + data + "\n");
            fw.close();

        } catch (IOException e) {
            System.out.println("Error writing to file");
        }
    }
}