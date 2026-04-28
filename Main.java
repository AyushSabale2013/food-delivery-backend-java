import model.*;
import service.*;
import java.util.*;
import java.io.*;

public class Main {

    static boolean isAdmin(String id, String pass) {
        return (id.equals("A101") && pass.equals("admin"));
    }

    // 🔹 REMOVE FOOD FROM FILE
    static void removeFood(String id) {
        try {
            List<String> lines = new ArrayList<>();

            BufferedReader br = new BufferedReader(new FileReader("data/menu.txt"));
            String line;

            while ((line = br.readLine()) != null) {
                if (!line.startsWith(id + ",")) {
                    lines.add(line);
                }
            }
            br.close();

            FileWriter fw = new FileWriter("data/menu.txt", false);
            for (String l : lines)
                fw.write(l + "\n");
            fw.close();

        } catch (Exception e) {
            System.out.println("Error removing food!");
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

                System.out.println("\n1 Register\n2 Login\n3 Admin Login\n4 Exit");

                int c = sc.nextInt();
                sc.nextLine();

                switch (c) {

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

                    case 2: {
                        System.out.print("User ID: "); String id = sc.nextLine();
                        System.out.print("Password: "); String pass = sc.nextLine();

                        current = us.login(id, pass);

                        if (current == null)
                            System.out.println("Login failed!");
                        break;
                    }

                    case 3: {
                        System.out.print("Admin ID: ");
                        String aid = sc.nextLine();

                        System.out.print("Pass: ");
                        String ap = sc.nextLine();

                        if (isAdmin(aid, ap)) {

                            System.out.println("Welcome Admin");

                            while (true) {

                                System.out.println("\n1 Add Food\n2 Remove Food\n3 Exit");

                                int ch = sc.nextInt();
                                sc.nextLine();

                                // 🔹 ADD FOOD
                                if (ch == 1) {
                                    while (true) {

                                        System.out.print("Enter item (id,name,price) or 0: ");
                                        String in = sc.nextLine();

                                        if (in.equals("0"))
                                            break;

                                        try {
                                            FileWriter fw = new FileWriter("data/menu.txt", true);
                                            fw.write(in + "\n");
                                            fw.close();
                                        } catch (Exception e) {
                                            System.out.println("Error writing to menu file!");
                                        }
                                    }

                                    r.loadMenuFromFile();
                                    System.out.println("Menu updated!");
                                }

                                // 🔹 REMOVE FOOD
                                if (ch == 2) {
                                    while (true) {

                                        System.out.print("Enter Food ID to remove or 0: ");
                                        String fid = sc.nextLine();

                                        if (fid.equals("0"))
                                            break;

                                        removeFood(fid);
                                    }

                                    r.loadMenuFromFile();
                                    System.out.println("Menu updated!");
                                }

                                if (ch == 3)
                                    break;
                            }

                        } else {
                            System.out.println("Invalid admin credentials");
                        }

                        break;
                    }

                    case 4:
                        sc.close();
                        return;

                    default:
                        System.out.println("Invalid choice!");
                }
            }

            // 🔹 USER LOOP
            while (current != null) {

                System.out.println("\n1 Menu 2 Add 3 Remove 4 Cart 5 Order 6 Logout");

                int ch = sc.nextInt();
                sc.nextLine();

                switch (ch) {

                    // 🔹 VIEW MENU
                    case 1:
                        for (FoodItem f : r.getMenu()) {
                            System.out.println(f.getItemId() + " - " + f.getName() + " - " + f.getPrice());
                        }
                        break;

                    // 🔥 ADD LOOP
                    case 2: {
                        System.out.println("\n--- ADD ITEMS (Enter 0 to exit) ---");

                        for (FoodItem f : r.getMenu()) {
                            System.out.println(f.getItemId() + " - " + f.getName() + " - " + f.getPrice());
                        }

                        while (true) {
                            System.out.print("Enter Food ID: ");
                            String id = sc.nextLine();

                            if (id.equals("0"))
                                break;

                            boolean found = false;

                            for (FoodItem f : r.getMenu()) {
                                if (f.getItemId().equalsIgnoreCase(id)) {
                                    cs.addItem(f);
                                    found = true;
                                    break;
                                }
                            }

                            if (!found)
                                System.out.println("Invalid Food ID!");
                        }
                        break;
                    }

                    // 🔥 REMOVE LOOP
                    case 3: {
                        System.out.println("\n--- REMOVE ITEMS (Enter 0 to exit) ---");

                        cs.viewCart();

                        while (true) {
                            System.out.print("Enter Food ID to remove: ");
                            String id = sc.nextLine();

                            if (id.equals("0"))
                                break;

                            if (!cs.removeItemById(id)) {
                                System.out.println("Item not in cart!");
                            }
                        }
                        break;
                    }

                    case 4:
                        cs.viewCart();
                        break;

                    case 5: {
                        if (cs.getCartItems().isEmpty()) {
                            System.out.println("Cart is empty!");
                            break;
                        }

                        System.out.print("Enter delivery address: ");
                        String addr = sc.nextLine();

                        Order o = os.placeOrder(cs.getCartItems(), current, addr);
                        o.displayOrder();

                        cs.clearCart();
                        break;
                    }

                    case 6:
                        current = null;
                        break;

                    default:
                        System.out.println("Invalid choice!");
                }
            }
        }
    }
}