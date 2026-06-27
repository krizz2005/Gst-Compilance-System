package com.gstsystem.model;

public class Invoice {
    private int id;
    private String customerName;
    private Product product;
    private int quantity;
    private double taxableValue;
    private double cgst;
    private double sgst;
    private double igst;
    private double totalAmount;
    private boolean isInterstate;

    public Invoice() {}

    // Encapsulated Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public double getTaxableValue() { return taxableValue; }
    public void setTaxableValue(double taxableValue) { this.taxableValue = taxableValue; }
    public double getCgst() { return cgst; }
    public void setCgst(double cgst) { this.cgst = cgst; }
    public double getSgst() { return sgst; }
    public void setSgst(double sgst) { this.sgst = sgst; }
    public double getIgst() { return igst; }
    public void setIgst(double igst) { this.igst = igst; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public boolean isInterstate() { return isInterstate; }
    public void setInterstate(boolean interstate) { isInterstate = interstate; }
}