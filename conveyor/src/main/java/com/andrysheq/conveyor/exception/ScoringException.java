package com.andrysheq.conveyor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ScoringException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -5545813528598254196L;

    public ScoringException(String exception) {
        super(exception);
    }

}