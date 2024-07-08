package com.andrysheq.deal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AgeNotAllowedException extends LoanDeniedException {
    public AgeNotAllowedException() {
        super("Возраст лица, получающего кредит должен быть от 20 до 60 лет");
    }
}
