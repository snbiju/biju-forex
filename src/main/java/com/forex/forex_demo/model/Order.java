package com.forex.forex_demo.model;

/**
 * Created By Biju Pillai
 */
public class Order {

    private String orderId;
    private String currency;
    private double price;
    private double amount;

    public Order() {
    }

    public Order(String orderId, String currency, double price, double amount) {
        this.orderId = orderId;
        this.currency = currency;
        this.price = price;
        this.amount = amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", currency='" + currency + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                '}';
    }

}
