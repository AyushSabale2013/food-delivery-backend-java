package model;

public class Admin extends User {

    public Admin(String id, String u, String p) {
        super(id, u, p);
    }

    @Override
    public void displayRole() {
        System.out.println("Role: Admin");
    }
}