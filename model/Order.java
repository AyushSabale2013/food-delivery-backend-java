package model;

import java.util.*;

public class Order implements Payable, Trackable {

    private static int counter = 1;

    private int orderId;
    private List<FoodItem> items;
    private String address;
    private double total;
    private User user;

    public Order(List<FoodItem> items, String address, User user) {
        this.orderId = counter++;
        this.items = new ArrayList<>(items);
        this.address = address;
        this.user = user;
        this.total = calculateTotal(); // 🔥 interface method
    }

    //  from Payable
    @Override
    public double calculateTotal() {
        double sum = 0;

        for (FoodItem f : items) {
            sum += f.getPrice();
        }

        if (user instanceof PremiumCustomer) {
            sum *= 0.7;
        }

        return sum;
    }

    //  from Trackable
    @Override
    public void trackOrder() {
        System.out.println("Order " + orderId + " is being processed...");
    }

    public int getOrderId() { return orderId; }
    public double getTotal() { return total; }

    public void displayOrder() {
        System.out.println("Order ID: " + orderId);
        System.out.println("Address: " + address);

        for (FoodItem f : items) {
            System.out.println(f.getName() + " - " + f.getPrice());
        }

        System.out.println("Total: " + total);
    }
}