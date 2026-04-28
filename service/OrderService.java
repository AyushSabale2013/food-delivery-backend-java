package service;

import model.*;
import java.util.*;

public class OrderService {

    private List<Order> orders = new ArrayList<>();

    public Order placeOrder(List<FoodItem> items, User user, String address) {

        // 🔹 Validate inputs
        if (user == null) {
            System.out.println("Invalid user!");
            return null;
        }

        if (items == null || items.isEmpty()) {
            System.out.println("No items in order!");
            return null;
        }

        if (address == null || address.trim().isEmpty()) {
            System.out.println("Invalid address!");
            return null;
        }

        try {
            // 🔹 Create order
            Order o = new Order(items, address, user);
            orders.add(o);

            // 🔹 Determine role
            String role = (user instanceof PremiumCustomer)
                    ? "PremiumCustomer"
                    : "Customer";

            // 🔹 Prepare log
            String logData =
                    "OrderID: " + o.getOrderId() +
                    " | Role: " + role +
                    " | UserID: " + user.getUserId() +
                    " | Address: " + address +
                    " | Total: " + o.getTotal();

            // 🔹 Logging (delegated)
            FileLogger.log("data/orders.txt", logData);

            return o;

        } catch (Exception e) {
            System.out.println("Error placing order!");
            return null;
        }
    }
}