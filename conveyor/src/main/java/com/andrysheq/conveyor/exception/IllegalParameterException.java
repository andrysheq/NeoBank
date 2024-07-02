package com.andrysheq.conveyor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IllegalParameterException extends RuntimeException {

    public IllegalParameterException(String exception) {
        super(exception);
    }

}
