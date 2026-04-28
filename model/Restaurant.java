package model;

import java.util.*;
import java.io.*;

public class Restaurant {

    private List<FoodItem> menu = new ArrayList<>();

    public void loadMenuFromFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("menu.txt"));
            String line;

            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                menu.add(new FoodItem(p[0], p[1], Double.parseDouble(p[2])));
            }

            br.close();
        } catch (Exception e) {
            System.out.println("Menu load error");
        }
    }

    public List<FoodItem> getMenu() {
        return menu;
    }
}