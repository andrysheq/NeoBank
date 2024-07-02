package com.andrysheq.conveyor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LoanDeniedException extends RuntimeException {
    public LoanDeniedException(String exception) {
        super(exception);
    }

}

