package model;

public class Customer extends User {
    private String address;
    private String phone;

    public Customer(String id, String u, String p, String addr, String ph) {
        super(id, u, p);
        this.address = addr;
        this.phone = ph;
    }

    public String getAddress() { return address; }
    public String getPhone() { return phone; }

    @Override
    public void displayRole() {
        System.out.println("Role: Customer");
    }
}