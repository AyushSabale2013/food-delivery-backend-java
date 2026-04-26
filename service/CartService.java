package service;

import model.FoodItem;
import java.util.*;

public class CartService {

    private List<FoodItem> cart = new ArrayList<>();

    // 🔹 Add item
    public void addItem(FoodItem item) {
        cart.add(item);
        System.out.println(item.getName() + " added to cart");
    }

    // 🔹 View cart
    public void viewCart() {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty");
            return;
        }

        double total = 0;

        System.out.println("\nYour Cart:");

        for (FoodItem item : cart) {
            // ✅ show ID also (important)
            System.out.println(item.getItemId() + " - " + item.getName() + " - " + item.getPrice());
            total += item.getPrice();
        }

        System.out.println("Items in cart: " + cart.size());
        System.out.println("Total: " + total);
    }

    // 🔹 Get items (for order)
    public List<FoodItem> getCartItems() {
        return cart;
    }

    // 🔹 Clear cart
    public void clearCart() {
        cart.clear();
    }

    // 🔹 Remove item by ID (NEW FEATURE)
    public void removeItemById(String foodId) {

        Iterator<FoodItem> it = cart.iterator();
        boolean found = false;

        while (it.hasNext()) {
            FoodItem item = it.next();

            // ✅ case-insensitive comparison
            if (item.getItemId().equalsIgnoreCase(foodId)) {
                it.remove();
                System.out.println(item.getName() + " removed from cart");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Item not found in your cart!");
        }
    }
}