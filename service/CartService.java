package service;

import model.FoodItem;
import java.util.*;

public class CartService {

    private List<FoodItem> cart = new ArrayList<>();

    public void addItem(FoodItem item) {
        cart.add(item);
        System.out.println("Added: " + item.getName());
    }

    public void viewCart() {
        double total = 0;

        for (FoodItem f : cart) {
            System.out.println(f.getItemId() + " - " + f.getName());
            total += f.getPrice();
        }

        System.out.println("Total: " + total);
    }

    public List<FoodItem> getCartItems() {
        return cart;
    }

    public void clearCart() {
        cart.clear();
    }

    public boolean removeItemById(String foodId) {

        Iterator<FoodItem> it = cart.iterator();

        while (it.hasNext()) {
            FoodItem item = it.next();

            if (item.getItemId().equalsIgnoreCase(foodId)) {
                it.remove();
                System.out.println(item.getName() + " removed from cart");
                return true;
            }
        }

        return false; // 🔥 important
    }
}