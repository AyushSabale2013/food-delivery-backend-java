package model;

public class User {
    private String userId;
    private String username;
    private String password;
    private String address;

    public User(String userId, String username, String password, String address) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.address = address;
    }

    public String getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getAddress() { return address; }
}