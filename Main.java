import model.*;
import service.*;
import java.util.*;
import java.io.*;

public class Main {

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

                System.out.println("\n1 Register\n2 Login\n3 Admin\n4 Exit");

                int c;
                try {
                    c = Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Enter a number.");
                    continue;
                }

                switch (c) {

                    // 🔹 REGISTER
                    case 1: {
                        System.out.print("Name: "); String n = sc.nextLine();
                        System.out.print("Pass: "); String p = sc.nextLine();
                        System.out.print("Addr: "); String a = sc.nextLine();
                        System.out.print("Phone: "); String ph = sc.nextLine();

                        System.out.print("Premium? (yes/no): ");
                        boolean prem = sc.nextLine().equalsIgnoreCase("yes");

                        us.register(n, p, a, ph, prem);
                        break;
                    }

                    // 🔹 LOGIN
                    case 2: {
                        System.out.print("User ID: ");
                        String id = sc.nextLine();

                        System.out.print("Password: ");
                        String pass = sc.nextLine();

                        current = us.login(id, pass);
                        break;
                    }

                    // 🔹 ADMIN PANEL
                    case 3: {
                        System.out.print("Admin ID: ");
                        String aid = sc.nextLine();

                        System.out.print("Password: ");
                        String ap = sc.nextLine();

                        String name = AdminService.authenticate(aid, ap);

                        if (name != null) {

                            System.out.println("Welcome Admin: " + name);

                            while (true) {

                                System.out.println("\n1 Add Food\n2 Add User\n3 Remove User\n4 Exit");

                                int ch;
                                try {
                                    ch = Integer.parseInt(sc.nextLine());
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid choice!");
                                    continue;
                                }

                                // 🔹 ADD FOOD
                                if (ch == 1) {

                                    System.out.println("Enter food details (0 to exit)");

                                    while (true) {

                                        System.out.print("Food Name: ");
                                        String fname = sc.nextLine();

                                        if (fname.equals("0")) break;

                                        double price;
                                        System.out.print("Price: ");
                                        try {
                                            price = Double.parseDouble(sc.nextLine());
                                        } catch (NumberFormatException e) {
                                            System.out.println("Invalid price!");
                                            continue;
                                        }

                                        String id = FoodService.generateFoodId();

                                        try (BufferedWriter bw = new BufferedWriter(new FileWriter("data/menu.txt", true))) {
                                            bw.write(id + "," + fname + "," + price);
                                            bw.newLine();
                                        } catch (IOException e) {
                                            System.out.println("Error writing menu file!");
                                        }

                                        System.out.println("Added: " + id + " - " + fname);
                                    }

                                    r.loadMenuFromFile();
                                }

                                // 🔹 ADD USER
                                if (ch == 2) {

                                    System.out.println("Add Users (0 to exit)");

                                    while (true) {

                                        System.out.print("Name: ");
                                        String n = sc.nextLine();
                                        if (n.equals("0")) break;

                                        System.out.print("Pass: ");
                                        String p = sc.nextLine();

                                        System.out.print("Addr: ");
                                        String a = sc.nextLine();

                                        System.out.print("Phone: ");
                                        String ph = sc.nextLine();

                                        System.out.print("Premium? (yes/no): ");
                                        boolean prem = sc.nextLine().equalsIgnoreCase("yes");

                                        us.register(n, p, a, ph, prem);
                                    }
                                }

                                // 🔹 REMOVE USER
                                if (ch == 3) {

                                    while (true) {

                                        System.out.print("Enter UserID to remove (0 to exit): ");
                                        String uid = sc.nextLine();

                                        if (uid.equals("0")) break;

                                        if (us.removeUser(uid))
                                            System.out.println("User removed!");
                                        else
                                            System.out.println("Invalid ID!");
                                    }
                                }

                                if (ch == 4) break;
                            }

                        } else {
                            System.out.println("Invalid admin credentials");
                        }
                        break;
                    }

                    case 4:
                        sc.close();
                        return;
                }
            }

            // 🔹 USER LOOP
            while (current != null) {

                System.out.println("\n1 Menu 2 Add 3 Remove 4 Cart 5 Order 6 Logout");

                int ch;
                try {
                    ch = Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input!");
                    continue;
                }

                switch (ch) {

                    // 🔹 VIEW MENU
                    case 1:
                        for (FoodItem f : r.getMenu())
                            System.out.println(f.getItemId() + " - " + f.getName() + " - " + f.getPrice());
                        break;

                    // 🔹 ADD TO CART
                    case 2: {
                        System.out.println("Enter ID (0 to exit)");

                        while (true) {
                            String id = sc.nextLine();

                            if (id.equals("0")) break;

                            boolean found = false;

                            for (FoodItem f : r.getMenu()) {
                                if (f.getItemId().equalsIgnoreCase(id)) {
                                    cs.addItem(f);
                                    found = true;
                                    break;
                                }
                            }

                            if (!found)
                                System.out.println("Invalid ID!");
                        }
                        break;
                    }

                    // 🔥 REMOVE MODE
                    case 3: {
                        System.out.println("\n--- REMOVE MODE ---");
                        System.out.println("Only way to exit is enter 0");

                        cs.viewCart();

                        while (true) {

                            System.out.print("Enter Food ID to remove: ");
                            String id = sc.nextLine();

                            if (id.equals("0")) break;

                            if (cs.removeItemById(id))
                                System.out.println("Item removed!");
                            else
                                System.out.println("Item not in cart!");
                        }
                        break;
                    }

                    // 🔹 VIEW CART
                    case 4:
                        cs.viewCart();
                        break;

                    // 🔹 PLACE ORDER
                    case 5: {
                        if (cs.getCartItems().isEmpty()) {
                            System.out.println("Cart empty!");
                            break;
                        }

                        System.out.print("Address: ");
                        String addr = sc.nextLine();

                        Order o = os.placeOrder(cs.getCartItems(), current, addr);

                        if (o != null)
                            o.displayOrder();

                        cs.clearCart();
                        break;
                    }

                    // 🔹 LOGOUT
                    case 6:
                        current = null;
                        break;
                }
            }
        }
    }
}