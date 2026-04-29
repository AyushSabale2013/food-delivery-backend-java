package service;

import java.io.*;

public class FoodService {

    public static String generateFoodId() {

        String last = null;

        // try-with-resources (auto close)
        try (BufferedReader br = new BufferedReader(new FileReader("data/menu.txt"))) {

            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();

                // ignore empty lines
                if (!line.isEmpty()) {
                    last = line;
                }
            }

        } 
        catch (FileNotFoundException e) {
            System.out.println("Menu file not found!");
            return "F101";
        } 
        catch (IOException e) {
            System.out.println("Error reading menu file!");
            return "F101";
        }

        //  safe handling after reading
        if (last == null) return "F101";

        try {
            String[] parts = last.split(",");
            String id = parts[0];

            int num = Integer.parseInt(id.substring(1));
            return "F" + (num + 1);

        } catch (Exception e) {
            System.out.println("Error parsing last food ID!");
            return "F101";
        }
    }
}