package com.andrysheq.deal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ScoringException extends RuntimeException {

    public ScoringException(String exception) {
        super(exception);
    }

}