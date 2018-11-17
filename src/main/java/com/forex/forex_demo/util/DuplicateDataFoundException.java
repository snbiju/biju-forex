package com.forex.forex_demo.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Biju Pillai
 */

@ResponseStatus(HttpStatus.FOUND)
public class DuplicateDataFoundException extends RuntimeException {

    public DuplicateDataFoundException() {
        super();
    }
    public DuplicateDataFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateDataFoundException(String message) {
        super(message);
    }

    public DuplicateDataFoundException(Throwable cause) {
        super(cause);
    }
}
