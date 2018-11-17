package com.forex.forex_demo.service;

import com.forex.forex_demo.model.Order;
import com.forex.forex_demo.util.DataNotFoundException;
import com.forex.forex_demo.util.DuplicateDataFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By Biju Pillai
 */

@Service
public class OrderService {
    public static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private static List<Order> orders = new ArrayList<>();

    public void create(Order order) {
        Order findOrder = getOrder(order.getOrderId());
        if (findOrder != null) {
            if (findOrder.getOrderId().equalsIgnoreCase(order.getOrderId())) {
                logger.error("Order already exists");
                throw new DuplicateDataFoundException("Unable to create order. Order Id already exists.");
            }
        }
        orders.add(order);
    }

    public Order findByOrderId(String orderId){
        Order order= getOrder(orderId) ;
        if(order == null){
            throw new DataNotFoundException("Order data not found");
        }
       return order;
    }

    private Order getOrder(String orderId){
        Order orderValue=null;
        if(orders != null) {
            for (Order order : orders) {
                if (order.getOrderId().equalsIgnoreCase(orderId)) {
                    logger.info("order info {}", order);
                    orderValue = order;
                }
            }
        }
        return orderValue;
    }

    public List<Order> findMisMatch(String orderId){
        if(orders == null){
            throw new DataNotFoundException("Order data not found");
        }
        List<Order> misMatchList = new ArrayList<Order>();

        for (Order order:orders) {
            if(!order.getOrderId().equalsIgnoreCase(orderId)){
                misMatchList.add(order);
            }
        }
        if(misMatchList.isEmpty()){
            throw new DataNotFoundException("Mismatch data not found");
        }
        return misMatchList;
    }

    public boolean isOrderExist(Order order) {
        return getOrder(order.getOrderId())!=null;
    }

    public void deleteOrder(Order order) {
       Order  delOrder = getOrder(order.getOrderId());

        if (null== delOrder ||!delOrder.getOrderId().equalsIgnoreCase(order.getOrderId())) {
            logger.error("Unable to delete. User with id {} not found.", order.getOrderId());
            throw new DataNotFoundException("Unable to delete. Order with id " + order.getOrderId() + " not found.");
        }
        orders.remove(delOrder);

    }

    public List<Order> findAll() {
        if (null==orders || orders.isEmpty()) {
            logger.error("No record found.");
            new DataNotFoundException("Record found not found.");
        }
        return orders;
    }
}
