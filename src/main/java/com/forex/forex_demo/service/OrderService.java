package com.forex.forex_demo.service;

import com.forex.forex_demo.model.Order;
import com.forex.forex_demo.dao.OrderDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created By Biju Pillai
 */

@Service
public class OrderService {
    public static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    public OrderDAO dao;

    @Autowired
    public OrderService(OrderDAO repository){
        this.dao= repository;
    }

    public void create(Order order) {
        dao.save(order);
        logger.info("order info {}", order);
    }


    public void cancelOrder(Order order) {
        dao.cancel(order);
        logger.info("Order has been cancelled");
    }

    public List<List<Order>>  findMatch(){
       return dao.findMatch();
    }

    public List<Order> findUnMatch(){
        return dao.findUnMatch();
    }

    public List<Order> findAll(){
        return dao.findAll();
    }

   /* public List<Order> findCancelled(){

        return dao.findCancelled();
    }*/

}
