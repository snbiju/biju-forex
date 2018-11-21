package com.forex.forex_demo.model;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Created By Biju Pillai
 */

@Component
public class Order {

    @ApiModelProperty(notes = "OrderId",value = "1" ,position = 1)
    @NotNull
    private String userId;

    @ApiModelProperty(notes ="Order Type", value = "BID/ASK",position = 2)
    private String orderType;

    @ApiModelProperty(notes = "currency",value = "GBP/USD",position = 3 )
    private String currency;

    @ApiModelProperty(notes = "price", value = "1.21" ,position = 4)
    private double price;

    @ApiModelProperty(notes = "amount", value = "100" ,position = 5)
    private double amount;

    public Order() {
    }

    public Order(@NotNull String userId,String orderType, String currency, double price, double amount) {
        this.userId = userId;
        this.orderType=orderType;
        this.currency = currency;
        this.price = price;
        this.amount = amount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return Double.compare(order.getPrice(), getPrice()) == 0 &&
                Double.compare(order.getAmount(), getAmount()) == 0 &&
                Objects.equals(getUserId(), order.getUserId()) &&
                Objects.equals(getOrderType(), order.getOrderType()) &&
                Objects.equals(getCurrency(), order.getCurrency());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getOrderType(), getCurrency(), getPrice(), getAmount());
    }

    @Override
    public String toString() {
        return "Order{" +
                "userId='" + userId + '\'' +
                ", orderType='" + orderType + '\'' +
                ", currency='" + currency + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                '}';
    }
}
