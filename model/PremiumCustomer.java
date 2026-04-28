package model;

public class PremiumCustomer extends Customer {

    public PremiumCustomer(String id, String u, String p, String addr, String ph) {
        super(id, u, p, addr, ph);
    }

    @Override
    public void displayRole() {
        System.out.println("Role: Premium Customer");
    }
}