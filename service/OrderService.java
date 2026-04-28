package service;

import model.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OrderService {

    private List<Order> orders = new ArrayList<>();

    public Order placeOrder(List<FoodItem> items, String userId, String address) {

        Order order = new Order(items, address);
        orders.add(order);

        // 🔥 Human-readable time
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a");

        String time = now.format(formatter);

        // 🔥 structured log
        String logData =
                "OrderID: " + order.getOrderId() +
                " | Time: " + time +
                " | UserID: " + userId +
                " | Address: " + address +
                " | Total: " + order.getTotal();

        FileLogger.log("orders.txt", logData);

        System.out.println("Order placed successfully!");

        return order;
    }
}