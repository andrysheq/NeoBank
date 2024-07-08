package com.andrysheq.application.service;

import com.andrysheq.application.dto.LoanApplicationRequestDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

@Service
public class ApplicationService {
    public void preScoring(LoanApplicationRequestDTO request) {
        assertName(request.getFirstName());
                assertLastName(request.getLastName());
                assertMiddleName(request.getMiddleName());
                assertLoanAmount(request.getAmount());
                assertLoanTerm(request.getTerm());
                assertBirthDate(request.getBirthDate());
                assertEmail(request.getEmail());
                assertPassportSeries(request.getPassportSeries());
                assertPassportNumber(request.getPassportNumber());
    }

    private void assertName(String name) {
        if(!(name != null && name.matches("[A-Za-z]{2,30}"))){
            throw new RuntimeException("Имя должно быть от 2 до 30 латинских букв.");
        }
    }

    private void assertLastName(String name) {
        if(!(name != null && name.matches("[A-Za-z]{2,30}"))){
            throw new RuntimeException("Фамилия должна быть от 2 до 30 латинских букв.");
        }
    }

    private void assertMiddleName(String middleName) {
        if(!(middleName == null || middleName.matches("[A-Za-z]{2,30}"))){
            throw new RuntimeException("Отчество (при наличии) должно быть от 2 до 30 латинских букв.");
        }
    }

    private void assertLoanAmount(BigDecimal amount) {
        if(!(amount != null && amount.compareTo(new BigDecimal("10000")) < 0)){
            throw new RuntimeException("Сумма кредита должна быть больше 10000");
        }
    }

    private void assertLoanTerm(Integer term) {
        if(!(term != null && term >= 6)){
            throw new RuntimeException("Срок кредита должен быть больше 6 месяцев");
        }
    }

    private void assertBirthDate(LocalDate birthDate) {
        if (birthDate == null) {
            throw new RuntimeException("Дата рождения не должна быть пустой");
        }
        LocalDate today = LocalDate.now();
        Period age = Period.between(birthDate, today);
        if(age.getYears() < 18){
            throw new RuntimeException("Клиент должен быть старше 18 лет");
        }
    }

    private void assertEmail(String email) {
        String emailRegex = "^[\\w\\.]{2,50}@[\\w\\.]{2,20}$";
        if(!(email != null && Pattern.matches(emailRegex, email))){
            throw new RuntimeException("Некорректно указан адрес электронной почты");
        }
    }

    private void assertPassportSeries(String series) {
        if(!(series != null && series.matches("\\d{4}"))){
            throw new RuntimeException("Серия паспорта - 4 цифры");
        }
    }

    private void assertPassportNumber(String number) {
        if(!(number != null && number.matches("\\d{6}"))){
            throw new RuntimeException("Номер паспорта - 6 цифры");
        }
    }
}
