package model;

import java.util.*;
import java.io.*;

public class Restaurant {
    private String name;
    private List<FoodItem> menu;

    public Restaurant(String name) {
        this.name = name;
        this.menu = new ArrayList<>();
    }

    public void loadMenuFromFile() {
        try {
            File file = new File("menu.txt");
            if (!file.exists()) {
                System.out.println("menu.txt not found!");
                return;
            }

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length < 3) continue;

                String id = parts[0];
                String name = parts[1];
                double price = Double.parseDouble(parts[2]);

                menu.add(new FoodItem(id, name, price));
            }

            br.close();

        } catch (Exception e) {
            System.out.println("Error loading menu file");
        }
    }

    public List<FoodItem> getMenu() {
        return menu;
    }

    public String getName() {
        return name;
    }
}