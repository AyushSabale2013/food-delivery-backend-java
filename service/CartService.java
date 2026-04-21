package service;

import model.FoodItem;
import java.util.*;

public class CartService {

    private List<FoodItem> cart = new ArrayList<>();

    public void addItem(FoodItem item) {
        cart.add(item);
        System.out.println(item.getName() + " added to cart");
    }

    public void viewCart() {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty");
            return;
        }

        double total = 0;
        for (FoodItem item : cart) {
            System.out.println(item.getName() + " - " + item.getPrice());
            total += item.getPrice();
        }

        System.out.println("Total: " + total);
    }

    public List<FoodItem> getCartItems() {
        return cart;
    }

    public void clearCart() {
        cart.clear();
    }
}