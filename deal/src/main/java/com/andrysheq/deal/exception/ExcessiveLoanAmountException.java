package com.andrysheq.deal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ExcessiveLoanAmountException extends LoanDeniedException {
    public ExcessiveLoanAmountException() {
        super("Сумма кредита не должна превышать 20 зарплат.");
    }
}
