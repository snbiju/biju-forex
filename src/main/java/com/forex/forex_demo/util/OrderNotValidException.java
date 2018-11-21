package com.forex.forex_demo.util;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Biju Pillai
 */


@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class OrderNotValidException extends RuntimeException {

    public OrderNotValidException(){ super();}

    public OrderNotValidException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderNotValidException(String message) {
        super(message);
    }

    public OrderNotValidException(Throwable cause) {
        super(cause);
    }
}
