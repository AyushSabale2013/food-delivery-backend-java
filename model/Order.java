package model;

import java.util.*;

public class Order {
    private static int counter = 1;
    private final int orderId;
    private List<FoodItem> items;
    private double total;

    public Order(List<FoodItem> items) {
        this.orderId = counter++;
        this.items = new ArrayList<>(items);
        this.total = calculateTotal();
    }

    private double calculateTotal() {
        double sum = 0;
        for (FoodItem item : items) {
            sum += item.getPrice();
        }
        return sum;
    }

    public int getOrderId() { return orderId; }
    public double getTotal() { return total; }

    public void displayOrder() {
        System.out.println("Order ID: " + orderId);
        for (FoodItem item : items) {
            System.out.println(item.getName() + " - " + item.getPrice());
        }
        System.out.println("Total: " + total);
    }
}