package com.forex.forex_demo.web;

import com.forex.forex_demo.model.Order;
import com.forex.forex_demo.service.OrderService;
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


    private OrderService orderService;

    @Autowired
    public ForexOrderController(OrderService orderService){
        this.orderService =orderService;
    }

    @ApiOperation(value ="Create new order" ,position = 1)
    @ApiResponses(value = {@ApiResponse(code = 201,message = "Order successfully created."),@ApiResponse(code =302,message = "Duplicate Order!.Order Already Exists!"),@ApiResponse(code =406,message = "Order not valid")})
    @RequestMapping(value="/create", method = RequestMethod.POST)
    public ResponseEntity<?> createOrder(@Valid @RequestBody Order order){
        orderService.create(order);
        return new ResponseEntity<String>("Order is created successfully",HttpStatus.CREATED);
    }

    @ApiOperation(value ="Delete Order" )
    @ApiResponses(value = {@ApiResponse(code = 204,message = "Order is cancelled successfully."),@ApiResponse(code =404,message = "Order Not Found!")})
    @RequestMapping(value = "/cancel", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteOrder(@Valid @RequestBody Order order) {
        orderService.cancelOrder(order);
        return new ResponseEntity<String>("Order is cancelled successfully",HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value ="Mismatch Order" ,position = 4)
    @ApiResponses(value = {@ApiResponse(code = 200,message = "Mismatched Order has been fetched."),@ApiResponse(code =404,message = "Order Not Found!")})
    @RequestMapping(value = "/unmatched", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findUnMatch() {

        List<Order> unMatchList = orderService.findUnMatch();
        logger.info("order information {}",unMatchList);
        return new ResponseEntity<List<Order>>(unMatchList, HttpStatus.OK);

    }
    @ApiOperation(value ="Find Order" ,position = 2)
    @ApiResponses(value = {@ApiResponse(code = 200,message = "Order has been fetched."),@ApiResponse(code =404,message = "Order Not Found!")})
    @RequestMapping(value = "/matched", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<?> findMatch() {
        List<List<Order>>  matchList = orderService.findMatch();
        logger.info("order information {}",matchList);
        return new ResponseEntity<List<List<Order>>>(matchList, HttpStatus.OK);

    }

    @ApiOperation(value ="Find All Orders", position = 3)
    @ApiResponses(value = {@ApiResponse(code = 200,message = "Order has been fetched.")})
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll() {

        List<Order> ordersList = orderService.findAll();

        return new ResponseEntity<List<Order>>(ordersList, HttpStatus.OK);

    }

}
