package model;

public class FoodItem {
    private String itemId;
    private String name;
    private double price;

    public FoodItem(String id, String name, double price) {
        this.itemId = id;
        this.name = name;
        this.price = price;
    }

    public String getItemId() { return itemId; }
    public String getName() { return name; }
    public double getPrice() { return price; }
}