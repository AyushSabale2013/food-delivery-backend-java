package service;

import java.io.*;

public class AdminService {

    public static String authenticate(String id, String pass) {

        // 🔥 try-with-resources (auto close file)
        try (BufferedReader br = new BufferedReader(new FileReader("data/admin.txt"))) {

            String line;

            while ((line = br.readLine()) != null) {

                String[] p = line.split(",");

                if (p.length >= 5 &&
                    p[0].equalsIgnoreCase(id) &&
                    p[4].equals(pass)) {

                    return p[1]; // admin name
                }
            }

        } 
        // 🔥 specific exception handling
        catch (FileNotFoundException e) {
            System.out.println("Admin file not found!");
        } 
        catch (IOException e) {
            System.out.println("Error reading admin file!");
        }

        return null;
    }
}