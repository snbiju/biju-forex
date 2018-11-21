package com.forex.forex_demo.dao;

import com.forex.forex_demo.model.Order;
import com.forex.forex_demo.util.DataNotFoundException;
import com.forex.forex_demo.util.DuplicateDataFoundException;
import com.forex.forex_demo.util.OrderNotValidException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Biju Pillai
 */

@Repository
public class OrderDAO {
    public static final Logger logger = LoggerFactory.getLogger(OrderDAO.class);
    private static List<Order> orders = new ArrayList<>();

    @Value("${forex.price}")
    private double currentPrice;

    public void save(Order order){
        if(orders!=null){
            if(orders.contains(order)){
                throw new DuplicateDataFoundException("Duplicate Order!.Order Already Exists!");
            }
        }
        if(order.getOrderType().equals("ASK")){
            orderValidation(order, currentPrice >= order.getPrice());
        }else if(order.getOrderType().equals("BID")){
            orderValidation(order, currentPrice <=order.getPrice());
        }

    }



    public Order findByName(String name){
        return null;
    }

    public List<Order> findUnMatch(){

        return getCollect()
                        .entrySet().stream().filter(e->e.getValue().size()==1)
                        .map(x -> x.getValue())
                        .flatMap(List::stream)
                        .collect(Collectors.toList());

    }

    public List<List<Order>> findMatch(){
       return getCollect()
                .entrySet().stream().filter(e->e.getValue().size()>1)
                .map(x -> x.getValue())
                .collect(Collectors.toList());



    }

    private static Map<Double, List<Order>> getCollect() {
        return orders.stream().collect(Collectors.groupingBy(Order::getAmount));
    }

    public void cancel(Order order){
        if(!orders.isEmpty()) {
            if(!orders.contains(order)){
                throw new DataNotFoundException("Order not found!");
            }
          orders.remove(order);
        }else {
             throw new DataNotFoundException("Order not found!");
        }


    }
    public List<Order> findAll(){
        return orders.stream().collect(Collectors.toList());
    }

    private void orderValidation(Order order, boolean isValid) {
        if (isValid) {
            orders.add(order);
        } else {
            throw new OrderNotValidException("Order not valid");
        }
    }

}
