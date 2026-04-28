package service;

import model.*;
import java.util.*;
import java.io.*;

public class UserService {

    private Map<String, User> users = new HashMap<>();
    private static int idCounter = 101;

    // 🔹 Constructor
    public UserService() {
        loadUsersFromFile();
    }

    // 🔹 Register user
    public void register(String username, String password, String address, String phone) {

        // 🔥 phone validation
        if (!phone.matches("\\d{10}")) {
            System.out.println("Invalid phone number!");
            return;
        }

        String userId = "U" + idCounter++;

        // 🔥 OOP (Customer extends User)
        User newUser = new Customer(userId, username, password, address, phone);
        users.put(userId, newUser);

        // 🔥 SAVE in correct format: id,name,phone,address,password
        try {
            FileWriter fw = new FileWriter("users.txt", true);
            fw.write(userId + "," + username + "," + phone + "," + address + "," + password + "\n");
            fw.close();
        } catch (IOException e) {
            System.out.println("Error saving user");
        }

        // 🔹 Logging
        FileLogger.log("login_history.txt",
                "REGISTER → UserID: " + userId);

        System.out.println("Registered successfully!");
        System.out.println("Your User ID: " + userId);
    }

    // 🔹 Login
    public User login(String userId, String password) {

        if (!users.containsKey(userId)) {
            System.out.println("User not found!");
            return null;
        }

        User user = users.get(userId);

        if (user.getPassword().equals(password)) {

            FileLogger.log("login_history.txt",
                    "LOGIN → UserID: " + userId);

            System.out.println("Login successful!");
            user.displayRole(); // 🔥 polymorphism
            return user;

        } else {
            System.out.println("Incorrect password!");
            return null;
        }
    }

    // 🔹 Load users from file
    private void loadUsersFromFile() {
        try {
            File file = new File("users.txt");
            if (!file.exists()) return;

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            int maxId = 100;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                // 🔥 expect 5 fields
                if (parts.length < 5) continue;

                // 🔥 correct order: id,name,phone,address,password
                String userId = parts[0];
                String username = parts[1];
                String phone = parts[2];
                String address = parts[3];
                String password = parts[4];

                users.put(userId,
                        new Customer(userId, username, password, address, phone));

                int num = Integer.parseInt(userId.substring(1));
                if (num > maxId) maxId = num;
            }

            br.close();

            // 🔥 continue ID sequence
            idCounter = maxId + 1;

        } catch (Exception e) {
            System.out.println("Error loading users");
        }
    }
}