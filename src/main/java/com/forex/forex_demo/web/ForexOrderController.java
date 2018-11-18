package com.forex.forex_demo.web;

import com.forex.forex_demo.model.Order;
import com.forex.forex_demo.service.OrderService;
import com.forex.forex_demo.util.DataNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created By Biju Pillai
 */

@Api(value = "http://localhost:8080/forex",description = "Forex Rest Endpoint")
@RestController
@RequestMapping("/forex")
public class ForexOrderController {
    public static final Logger logger = LoggerFactory.getLogger(ForexOrderController.class);

    @Autowired
    private OrderService service;

    @ApiOperation(value ="Create new order")
    @ApiResponses(value = {@ApiResponse(code = 201,message = "Order successfully created."),@ApiResponse(code =301,message = "Order Already Exists!")})
    @RequestMapping(value="/order", method = RequestMethod.POST)
    public ResponseEntity<?> createOrder(@Valid @RequestBody Order order){
        service.create(order);
        return new ResponseEntity<String>("Order is created successfully",HttpStatus.CREATED);
    }

    @ApiOperation(value ="Delete Order")
    @ApiResponses(value = {@ApiResponse(code = 204,message = "Order is cancelled successfully."),@ApiResponse(code =404,message = "Order Not Found!")})
    @RequestMapping(value = "/cancel", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteOrder(@Valid @RequestBody Order order) {
        service.deleteOrder(order);
        return new ResponseEntity<String>("Order is cancelled successfully",HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value ="Mismatch Order")
    @ApiResponses(value = {@ApiResponse(code = 200,message = "Mismatched Order has been fetched."),@ApiResponse(code =404,message = "Order Not Found!")})
    @RequestMapping(value = "/mismatch/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findMisMatch(@Valid @PathVariable("id") String id) {

        List<Order> misMatchList = service.findMisMatch(id);

        return new ResponseEntity<List<Order>>(misMatchList, HttpStatus.OK);

    }
    @ApiOperation(value ="Find Order")
    @ApiResponses(value = {@ApiResponse(code = 200,message = "Order has been fetched."),@ApiResponse(code =404,message = "Order Not Found!")})
    @RequestMapping(value = "/order/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findMatch(@Valid @PathVariable("id") String id) {

        Order order = service.findByOrderId(id);
        logger.info("order information: {}",order);
        return new ResponseEntity<Order>(order, HttpStatus.OK);

    }

    @ApiOperation(value ="Find All Orders")
    @ApiResponses(value = {@ApiResponse(code = 200,message = "Order has been fetched."),@ApiResponse(code =404,message = "Order Not Found!")})
    @RequestMapping(value = "/order", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll() {

        List<Order> ordersList = service.findAll();

        return new ResponseEntity<List<Order>>(ordersList, HttpStatus.OK);

    }

}
