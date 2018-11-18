package com.forex.forex_demo.model;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * Created By Biju Pillai
 */

@Component
public class Order {

    @ApiModelProperty(notes = "OrderId",value = "1" ,position = 1)
    @NotNull
    private String orderId;
    @ApiModelProperty(notes = "currency",value = "GBP",position = 2 )
    private String currency;
    @ApiModelProperty(hidden = true)
    private double price;
    @ApiModelProperty(notes = "amount", value = "3.450" ,position = 3)
    private double amount;

    public Order() {
    }

    public Order(@NotNull String orderId, String currency, double amount) {
        this.orderId = orderId;
        this.currency = currency;
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
