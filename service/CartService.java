package service;

import model.FoodItem;
import java.util.*;

public class CartService {

    private List<FoodItem> cart = new ArrayList<>();

    // 🔹 Add item safely
    public void addItem(FoodItem item) {
        if (item == null) {
            System.out.println("Invalid item!");
            return;
        }

        cart.add(item);
        System.out.println("Added: " + item.getName());
    }

    // 🔹 View cart with empty check
    public void viewCart() {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty!");
            return;
        }

        double total = 0;

        for (FoodItem f : cart) {
            System.out.println(f.getItemId() + " - " + f.getName() + " - " + f.getPrice());
            total += f.getPrice();
        }

        System.out.println("Total: " + total);
    }

    // 🔹 Return copy (encapsulation safety)
    public List<FoodItem> getCartItems() {
        return new ArrayList<>(cart);
    }

    // 🔹 Clear cart
    public void clearCart() {
        cart.clear();
    }

    // 🔹 Remove item safely
    public boolean removeItemById(String id) {

        if (id == null || id.trim().isEmpty()) {
            System.out.println("Invalid ID!");
            return false;
        }

        Iterator<FoodItem> it = cart.iterator();

        while (it.hasNext()) {
            FoodItem f = it.next();

            if (f.getItemId().equalsIgnoreCase(id)) {
                it.remove();
                return true;
            }
        }

        return false;
    }
}