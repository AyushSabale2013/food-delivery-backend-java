package service;

import model.*;
import java.util.*;

public class OrderService {

    private List<Order> orders = new ArrayList<>();

    // 🔥 updated method (added address)
    public Order placeOrder(List<FoodItem> items, String userId, String address) {

        // pass address to Order
        Order order = new Order(items, address);
        orders.add(order);

        // 🔥 improved logging
        FileLogger.log("orders.txt",
                "UserID: " + userId +
                " | OrderID: " + order.getOrderId() +
                " | Address: " + address +
                " | Total: " + order.getTotal());

        System.out.println("Order placed successfully!");
        return order;
    }
}