import model.*;
import service.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        UserService userService = new UserService();
        CartService cartService = new CartService();
        OrderService orderService = new OrderService();

        // 🔹 Load menu from file
        Restaurant r1 = new Restaurant("Food Hub");
        r1.loadMenuFromFile();

        User currentUser = null;

        // 🔁 OUTER LOOP
        while (true) {

            // 🔹 AUTH LOOP
            while (currentUser == null) {
                System.out.println("\n1. Register\n2. Login\n3. Exit");

                int choice = sc.nextInt();

                switch (choice) {

                    case 1:
                        System.out.print("User ID (U101): ");
                        String id = sc.next();

                        System.out.print("Username: ");
                        String username = sc.next();

                        System.out.print("Password: ");
                        String password = sc.next();

                        System.out.print("Address: ");
                        String address = sc.next();

                        userService.register(id, username, password, address);
                        break;

                    case 2:
                        System.out.print("User ID: ");
                        id = sc.next();

                        System.out.print("Password: ");
                        password = sc.next();

                        currentUser = userService.login(id, password);

                        if (currentUser == null) {
                            System.out.println("Login failed. Try again.");
                        }
                        break;

                    case 3:
                        System.out.println("Exiting...");
                        System.exit(0);

                    default:
                        System.out.println("Invalid choice!");
                }
            }

            // 🔹 SHOPPING LOOP
            while (currentUser != null) {
                System.out.println("\n1.View Menu\n2.Add to Cart\n3.View Cart\n4.Place Order\n5.Logout");

                int choice = sc.nextInt();

                switch (choice) {

                    // ✅ VIEW MENU LOOP (stays until exit)
                    case 1:
                        while (true) {
                            System.out.println("\nMenu (Enter 0 to exit):");

                            for (FoodItem item : r1.getMenu()) {
                                System.out.println(item.getItemId() + " - " + item.getName() + " - " + item.getPrice());
                            }

                            String exitChoice = sc.next();

                            if (exitChoice.equals("0")) break;
                        }
                        break;

                    // ✅ ADD USING FOOD ID
                    case 2:
                        System.out.print("Enter Food ID (e.g., F101): ");
                        String foodId = sc.next();

                        boolean found = false;

                        for (FoodItem item : r1.getMenu()) {
                            if (item.getItemId().equals(foodId)) {
                                cartService.addItem(item);
                                found = true;
                                break;
                            }
                        }

                        if (!found) {
                            System.out.println("Invalid Food ID!");
                        }
                        break;

                    case 3:
                        cartService.viewCart();
                        break;

                    case 4:
                        if (cartService.getCartItems().isEmpty()) {
                            System.out.println("Cart is empty");
                            break;
                        }

                        Order order = orderService.placeOrder(cartService.getCartItems());
                        order.displayOrder();
                        cartService.clearCart();
                        break;

                    case 5:
                        System.out.println("Logged out!");
                        currentUser = null;
                        break;

                    default:
                        System.out.println("Invalid choice!");
                }
            }
        }
    }
}