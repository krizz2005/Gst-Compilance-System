package com.gstsystem.model;

public class Product {
    private int id;
    private String name;
    private String hsnCode;
    private double price;
    private double gstRate;

    public Product() {}

    public Product(int id, String name, String hsnCode, double price, double gstRate) {
        this.id = id;
        this.name = name;
        this.hsnCode = hsnCode;
        this.price = price;
        this.gstRate = gstRate;
    }

    // Encapsulated Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getHsnCode() { return hsnCode; }
    public void setHsnCode(String hsnCode) { this.hsnCode = hsnCode; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public double getGstRate() { return gstRate; }
    public void setGstRate(double gstRate) { this.gstRate = gstRate; }
}