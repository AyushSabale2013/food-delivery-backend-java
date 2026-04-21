package service;

import model.User;
import java.util.*;
import java.io.*;

public class UserService {

    private Map<String, User> users = new HashMap<>();

    // 🔹 Constructor → load users from file
    public UserService() {
        loadUsersFromFile();
    }

    // 🔹 Validate ID (U101 format)
    private boolean isValidId(String userId) {
        return userId.matches("U\\d{3}");
    }

    // 🔹 Register user
    public void register(String userId, String username, String password, String address) {

        if (!isValidId(userId)) {
            System.out.println("Invalid ID! Use format like U101");
            return;
        }

        if (users.containsKey(userId)) {
            System.out.println("User ID already exists!");
            return;
        }

        User newUser = new User(userId, username, password, address);
        users.put(userId, newUser);

        // Save to file
        try {
            FileWriter fw = new FileWriter("users.txt", true);
            fw.write(userId + "," + username + "," + password + "," + address + "\n");
            fw.close();
        } catch (IOException e) {
            System.out.println("Error saving user");
        }

        System.out.println("User registered successfully!");
    }

    // 🔹 Login user
    public User login(String userId, String password) {

        if (!users.containsKey(userId)) {
            System.out.println("User ID not found!");
            return null;
        }

        User user = users.get(userId);

        if (user.getPassword().equals(password)) {

            FileLogger.log("login_history.txt",
                "UserID: " + userId + " logged in");

            System.out.println("Login successful!");
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

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length < 4) continue;

                String userId = parts[0];
                String username = parts[1];
                String password = parts[2];
                String address = parts[3];

                users.put(userId, new User(userId, username, password, address));
            }

            br.close();

        } catch (IOException e) {
            System.out.println("Error reading users file");
        }
    }
}