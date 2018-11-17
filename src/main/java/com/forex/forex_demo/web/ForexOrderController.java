package com.forex.forex_demo.web;

import com.forex.forex_demo.model.Order;
import com.forex.forex_demo.service.OrderService;
import com.forex.forex_demo.util.DataNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created By Biju Pillai
 */

@RestController
public class ForexOrderController {
    public static final Logger logger = LoggerFactory.getLogger(ForexOrderController.class);

    @Autowired
    private OrderService service;

    @RequestMapping(value="forex/order", method = RequestMethod.POST)
    public ResponseEntity<?> createOrder(@RequestBody Order order){
        service.create(order);
        return new ResponseEntity<String>("Order is created successfully",HttpStatus.CREATED);
    }

    @RequestMapping(value = "forex/cancel", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteOrder(@RequestBody Order order) {
        service.deleteOrder(order);
        return new ResponseEntity<String>("Order is cancelled successfully",HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "forex/mismatch/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findMisMatch(@PathVariable("id") String id) {

        List<Order> misMatchList = service.findMisMatch(id);

        return new ResponseEntity<List<Order>>(misMatchList, HttpStatus.OK);

    }

    @RequestMapping(value = "forex/order/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findMatch(@PathVariable("id") String id) {

        Order order = service.findByOrderId(id);
        logger.info("order information: {}",order);
        return new ResponseEntity<Order>(order, HttpStatus.OK);

    }
    @RequestMapping(value = "forex/order", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll() {

        List<Order> ordersList = service.findAll();

        return new ResponseEntity<List<Order>>(ordersList, HttpStatus.OK);

    }

}
