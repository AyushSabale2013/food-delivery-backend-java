import model.*;
import service.*;
import java.util.*;

public class Main {

    // 🔹 RULEBOOK
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

        UserService us = new UserService();
        CartService cs = new CartService();
        OrderService os = new OrderService();

        Restaurant r = new Restaurant();
        r.loadMenuFromFile();

        User current = null;

        while (true) {

            // 🔹 AUTH LOOP
            while (current == null) {

                System.out.println("\n1. Register\n2. Login\n3. View Rulebook\n4. Exit");

                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {

                    case 1: {
                        System.out.print("Username: ");
                        String name = sc.nextLine();

                        System.out.print("Password: ");
                        String pass = sc.nextLine();

                        System.out.print("Address: ");
                        String addr = sc.nextLine();

                        System.out.print("Phone: ");
                        String phone = sc.nextLine();

                        us.register(name, pass, addr, phone);
                        break;
                    }

                    case 2: {
                        System.out.print("User ID: ");
                        String id = sc.nextLine();

                        System.out.print("Password: ");
                        String pass = sc.nextLine();

                        current = us.login(id, pass);

                        if (current == null) {
                            System.out.println("Login failed!");
                        }
                        break;
                    }

                    case 3:
                        showRulebook();
                        break;

                    case 4:
                        System.out.println("Exiting...");
                        sc.close();
                        return;

                    default:
                        System.out.println("Invalid choice!");
                }
            }

            // 🔹 MAIN MENU
            while (current != null) {

                System.out.println(
                        "\n--- MAIN MENU ---\n" +
                                "1. View Menu\n" +
                                "2. Add to Cart\n" +
                                "3. Remove Item\n" +
                                "4. View Cart\n" +
                                "5. Place Order\n" +
                                "6. Logout");

                int ch = sc.nextInt();
                sc.nextLine();

                switch (ch) {

                    // 🔹 VIEW MENU
                    case 1:
                        for (FoodItem f : r.getMenu()) {
                            System.out.println(f.getItemId() + " - " + f.getName() + " - " + f.getPrice());
                        }
                        break;

                    // 🔹 ADD TO CART (IMPROVED LOOP)
                    case 2: {

                        System.out.println("\n--- ADD ITEMS (Enter 0 to exit) ---");

                        for (FoodItem f : r.getMenu()) {
                            System.out.println(f.getItemId() + " - " + f.getName() + " - " + f.getPrice());
                        }

                        while (true) {

                            System.out.print("Enter Food ID: ");
                            String addId = sc.nextLine();

                            if (addId.equals("0")) {
                                System.out.println("Exiting add mode...");
                                break;
                            }

                            boolean found = false;

                            for (FoodItem f : r.getMenu()) {
                                if (f.getItemId().equalsIgnoreCase(addId)) {
                                    cs.addItem(f);
                                    found = true;
                                    break;
                                }
                            }

                            if (!found) {
                                System.out.println("Invalid Food ID! Try again.");
                            }
                        }

                        break;
                    }

                    // 🔹 REMOVE ITEM
                    case 3: {

                        if (cs.getCartItems().isEmpty()) {
                            System.out.println("Cart is empty!");
                            break;
                        }

                        System.out.println("\n--- REMOVE ITEMS (Enter 0 to exit) ---");

                        // show cart items
                        cs.viewCart();

                        while (true) {

                            System.out.print("Enter Food ID to remove: ");
                            String remId = sc.nextLine();

                            // 🔥 exit condition
                            if (remId.equals("0")) {
                                System.out.println("Exiting remove mode...");
                                break;
                            }

                            boolean removed = cs.removeItemById(remId);

                            if (!removed) {
                                System.out.println("Item not found in your cart! Try again.");
                            }
                        }

                        break;
                    }

                    // 🔹 VIEW CART
                    case 4:
                        cs.viewCart();
                        break;

                    // 🔹 PLACE ORDER (IMPROVED LOOP)
                    case 5: {

                        if (cs.getCartItems().isEmpty()) {
                            System.out.println("Cart is empty!");
                            break;
                        }

                        System.out.print("Enter delivery address: ");
                        String addr = sc.nextLine();

                        // 🔥 place order only once
                        Order o = os.placeOrder(cs.getCartItems(), current.getUserId(), addr);
                        o.displayOrder();

                        cs.clearCart();

                        System.out.println("Order placed successfully!");

                        break;
                    }

                    // 🔹 LOGOUT
                    case 6:
                        System.out.println("Logged out!");
                        current = null;
                        break;

                    default:
                        System.out.println("Invalid choice!");
                }
            }
        }
    }
}