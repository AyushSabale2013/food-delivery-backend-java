import model.*;
import service.*;
import java.util.*;

public class Main {

    // 🔹 RULEBOOK METHOD
    static void showRulebook() {
        try {
            java.io.BufferedReader br = new java.io.BufferedReader(
                    new java.io.FileReader("rulebook.txt"));

            String line;
            System.out.println("\n--- RULEBOOK ---");

            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }

            br.close();

        } catch (Exception e) {
            System.out.println("Rulebook not found!");
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        UserService userService = new UserService();
        CartService cartService = new CartService();
        OrderService orderService = new OrderService();

        // 🔹 Load menu
        Restaurant r1 = new Restaurant("Food Hub");
        r1.loadMenuFromFile();

        if (r1.getMenu().isEmpty()) {
            System.out.println("Menu not loaded properly!");
        }

        User currentUser = null;

        // 🔁 OUTER LOOP
        while (true) {

            // 🔹 AUTH LOOP
            while (currentUser == null) {

                System.out.println("\n1. Register\n2. Login\n3. View Rulebook\n4. Exit");

                int choice = sc.nextInt();
                sc.nextLine(); // clear buffer

                switch (choice) {

                    // 🔹 REGISTER
                    case 1: {
                        System.out.print("Username: ");
                        String username = sc.nextLine();

                        System.out.print("Password: ");
                        String password = sc.nextLine();

                        System.out.print("Address: ");
                        String address = sc.nextLine();

                        System.out.print("Phone Number: ");
                        String phone = sc.nextLine();

                        userService.register(username, password, address, phone);
                        break;
                    }

                    // 🔹 LOGIN
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

                    // 🔹 RULEBOOK
                    case 3:
                        showRulebook();
                        break;

                    // 🔹 EXIT
                    case 4:
                        System.out.println("Exiting...");
                        System.exit(0);

                    default:
                        System.out.println("Invalid choice!");
                }
            }

            // 🔹 SHOPPING LOOP
            while (currentUser != null) {

                System.out.println(
                        "\n1.View Menu\n2.Add to Cart\n3.Remove Item\n4.View Cart\n5.Place Order\n6.Logout");

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

                            System.out.print("Enter 0 to exit: ");
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
                                if (item.getItemId().equalsIgnoreCase(foodId)) {
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

                    // 🔹 REMOVE ITEM
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

                        System.out.print("Enter delivery address: ");
                        String address = sc.nextLine();

                        Order order = orderService.placeOrder(
                                cartService.getCartItems(),
                                currentUser.getUserId(),
                                address);

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