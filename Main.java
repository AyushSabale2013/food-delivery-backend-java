import model.*;
import service.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        UserService userService = new UserService();
        CartService cartService = new CartService();
        OrderService orderService = new OrderService();

        // Load menu from file
        Restaurant r1 = new Restaurant("Food Hub");
        r1.loadMenuFromFile();

        User currentUser = null;

        // 🔁 OUTER LOOP
        while (true) {

            // 🔹 AUTH LOOP
            while (currentUser == null) {
                System.out.println("\n1. Register\n2. Login\n3. Exit");

                int choice = sc.nextInt();
                sc.nextLine(); // 🔥 clear buffer

                switch (choice) {

                    case 1: {
                        System.out.print("Username: ");
                        String username = sc.nextLine();

                        System.out.print("Password: ");
                        String password = sc.nextLine();

                        System.out.print("Address: ");
                        String address = sc.nextLine();

                        userService.register(username, password, address);
                        break;
                    }

                    case 2: {
                        System.out.print("User ID: ");
                        String id = sc.nextLine();

                        System.out.print("Password: ");
                        String password = sc.nextLine();

                        currentUser = userService.login(id, password);

                        if (currentUser == null) {
                            System.out.println("Login failed. Try again.");
                        }
                        break;
                    }

                    case 3:
                        System.out.println("Exiting...");
                        System.exit(0);

                    default:
                        System.out.println("Invalid choice!");
                }
            }

            // 🔹 SHOPPING LOOP
            while (currentUser != null) {
                System.out.println("\n1.View Menu\n2.Add to Cart\n3.Remove Item\n4.View Cart\n5.Place Order\n6.Logout");

                int choice = sc.nextInt();
                sc.nextLine(); // clear buffer

                switch (choice) {

                    // 🔹 VIEW MENU
                    case 1: {
                        while (true) {
                            System.out.println("\nMenu (Enter 0 to exit):");

                            for (FoodItem item : r1.getMenu()) {
                                System.out.println(item.getItemId() + " - " + item.getName() + " - " + item.getPrice());
                            }

                            String exitChoice = sc.nextLine();
                            if (exitChoice.equals("0"))
                                break;
                        }
                        break;
                    }

                    // 🔹 ADD TO CART
                    case 2: {
                        System.out.println("\nAdd Items (Enter 0 to exit):");

                        for (FoodItem item : r1.getMenu()) {
                            System.out.println(item.getItemId() + " - " + item.getName() + " - " + item.getPrice());
                        }

                        while (true) {
                            System.out.print("Enter Food ID: ");
                            String foodId = sc.nextLine();

                            if (foodId.equals("0"))
                                break;

                            boolean found = false;

                            for (FoodItem item : r1.getMenu()) {
                                if (item.getItemId().equalsIgnoreCase(foodId)) { // ✅ improved
                                    cartService.addItem(item);
                                    found = true;
                                    break;
                                }
                            }

                            if (!found) {
                                System.out.println("Invalid Food ID!");
                            }
                        }
                        break;
                    }

                    // 🔹 REMOVE ITEM (NEW FEATURE)
                    case 3: {
                        System.out.print("Enter Food ID to remove: ");
                        String foodId = sc.nextLine();

                        cartService.removeItemById(foodId);
                        break;
                    }

                    // 🔹 VIEW CART
                    case 4:
                        cartService.viewCart();
                        break;

                    // 🔹 PLACE ORDER
                    case 5: {
                        if (cartService.getCartItems().isEmpty()) {
                            System.out.println("Cart is empty");
                            break;
                        }

                        Order order = orderService.placeOrder(
                                cartService.getCartItems(),
                                currentUser.getUserId());

                        order.displayOrder();
                        cartService.clearCart();
                        break;
                    }

                    // 🔹 LOGOUT
                    case 6:
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