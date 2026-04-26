package service;

import model.User;
import java.util.*;
import java.io.*;

public class UserService {

    private Map<String, User> users = new HashMap<>();

    // 🔥 static counter for auto ID
    private static int idCounter = 101;

    // 🔹 Constructor → load users from file
    public UserService() {
        loadUsersFromFile();
    }

    // 🔹 Register user
    public void register(String username, String password, String address, String phone) {

        // 🔥 simple phone validation (optional but good)
        if (!phone.matches("\\d{10}")) {
            System.out.println("Invalid phone number! Must be 10 digits.");
            return;
        }

        // 🔥 auto-generate ID
        String userId = "U" + idCounter++;

        User newUser = new User(userId, username, password, address, phone);
        users.put(userId, newUser);

        // 🔹 Save to file
        try {
            FileWriter fw = new FileWriter("users.txt", true);
            fw.write(userId + "," + username + "," + password + "," + address + "," + phone + "\n");
            fw.close();
        } catch (IOException e) {
            System.out.println("Error saving user");
        }

        System.out.println("User registered successfully!");
        System.out.println("Your User ID is: " + userId);

        FileLogger.log("login_history.txt",
                "NEW USER REGISTERED → UserID: " + userId);
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
                    "LOGIN → UserID: " + userId);

            System.out.println("Login successful!");
            return user;

        } else {
            System.out.println("Incorrect password!");
            return null;
        }
    }

    // 🔹 Load users from file + fix counter
    private void loadUsersFromFile() {
        try {
            File file = new File("users.txt");
            if (!file.exists())
                return;

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            int maxId = 100;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                // 🔥 must be 5 now (with phone)
                if (parts.length < 5)
                    continue;

                String userId = parts[0];
                String username = parts[1];
                String password = parts[2];
                String address = parts[3];
                String phone = parts[4];

                users.put(userId, new User(userId, username, password, address, phone));

                // 🔥 update max ID
                int num = Integer.parseInt(userId.substring(1));
                if (num > maxId)
                    maxId = num;
            }

            br.close();

            // 🔥 continue ID sequence
            idCounter = maxId + 1;

        } catch (IOException e) {
            System.out.println("Error reading users file");
        }
    }
}