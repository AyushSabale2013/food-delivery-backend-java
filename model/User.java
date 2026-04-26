package model;

public class User {
    private String userId;
    private String username;
    private String password;
    private String phone;
    private String address;

    public User(String userId, String username, String password, String address, String phone) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.address = address;
        this.phone = phone;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }
}