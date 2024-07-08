package com.andrysheq.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnemployedException extends LoanDeniedException {
    public UnemployedException() {
        super("Физическое лицо, получающее кредит, должно быть трудоустроено.");
    }
}
