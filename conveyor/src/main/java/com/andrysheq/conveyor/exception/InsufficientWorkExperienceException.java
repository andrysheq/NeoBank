package com.andrysheq.conveyor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InsufficientWorkExperienceException extends LoanDeniedException {
    public InsufficientWorkExperienceException() {
        super("Общий стаж работы должен быть не менее 12 месяцев, а текущий стаж не меньше 3 месяцев.");
    }
}
