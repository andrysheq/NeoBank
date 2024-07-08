package com.andrysheq.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IllegalParameterException extends RuntimeException {

    public IllegalParameterException(String exception) {
        super(exception);
    }

}
