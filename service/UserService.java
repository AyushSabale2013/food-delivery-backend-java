package service;

import model.*;
import java.util.*;
import java.io.*;

public class UserService {

    private Map<String, User> users = new HashMap<>();
    private static int idCounter = 101;

    public UserService() {
        loadUsersFromFile();
    }

    public void register(String username, String password, String address, String phone, boolean isPremium) {

        if (!phone.matches("\\d{10}")) {
            System.out.println("Invalid phone number!");
            return;
        }

        String userId = "U" + idCounter++;

        User newUser = isPremium
                ? new PremiumCustomer(userId, username, password, address, phone)
                : new Customer(userId, username, password, address, phone);

        users.put(userId, newUser);

        try {
            FileWriter fw = new FileWriter("data/users.txt", true);
            fw.write(userId + "," + username + "," + phone + "," + address + "," + password + ","
                    + (isPremium ? "P" : "N") + "\n");
            fw.close();
        } catch (IOException e) {
            System.out.println("Error saving user");
        }

        FileLogger.log("data/login_history.txt",
                "REGISTER (Customer) → UserID: " + userId);

        System.out.println("Registered! Your ID: " + userId);
    }

    public User login(String userId, String password) {

        User user = users.get(userId);

        if (user != null && user.getPassword().equals(password)) {

            String role = (user instanceof PremiumCustomer) ? "PremiumCustomer" : "Customer";

            FileLogger.log("data/login_history.txt",
                    "LOGIN (" + role + ") → UserID: " + userId);

            System.out.println("Login successful!");
            user.displayRole();
            return user;
        }

        System.out.println("Login failed!");
        return null;
    }

    public boolean removeUser(String uid) {
        boolean found = false;

        try {
            List<String> lines = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader("data/users.txt"));
            String line;

            while ((line = br.readLine()) != null) {
                if (line.startsWith(uid + ",")) {
                    found = true;
                } else {
                    lines.add(line);
                }
            }
            br.close();

            if (found) {
                FileWriter fw = new FileWriter("data/users.txt", false);
                for (String l : lines)
                    fw.write(l + "\n");
                fw.close();
            }

        } catch (Exception e) {
            System.out.println("Error removing user!");
        }

        return found;
    }

    private void loadUsersFromFile() {
        try {
            File file = new File("data/users.txt");
            if (!file.exists())
                return;

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            int maxId = 100;

            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length < 6)
                    continue;

                boolean isPremium = p[5].equals("P");

                User u = isPremium
                        ? new PremiumCustomer(p[0], p[1], p[4], p[3], p[2])
                        : new Customer(p[0], p[1], p[4], p[3], p[2]);

                users.put(p[0], u);

                int num = Integer.parseInt(p[0].substring(1));
                if (num > maxId)
                    maxId = num;
            }

            idCounter = maxId + 1;

        } catch (Exception e) {
            System.out.println("Error loading users");
        }
    }
}