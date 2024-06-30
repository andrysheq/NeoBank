package com.andrysheq.conveyor.service.impl;

import com.andrysheq.conveyor.dto.CreditDTO;
import com.andrysheq.conveyor.dto.ScoringDataDTO;
import com.andrysheq.conveyor.service.ScoringService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Service
@RequiredArgsConstructor
@Log4j2
public class ScoringServiceImpl implements ScoringService {

    @Value("${default.rate}")
    private BigDecimal defaultRate;
    public CreditDTO scoring(ScoringDataDTO request){
        CreditDTO result = new CreditDTO();
        result.setAmount(request.getAmount());
        result.setTerm(request.getTerm());
        result.setIsInsuranceEnabled(request.getIsInsuranceEnabled());
        result.setIsSalaryClient(request.getIsSalaryClient());
        BigDecimal rate = defaultRate;

        switch(request.getEmployment().getEmploymentStatus()){
            case UNEMPLOYED ->{
                result=null;
            }
            case SELF_EMPLOYED -> {
                rate.add(new BigDecimal(1));
            }
            case BUSINESS_OWNER -> {
                rate.add(new BigDecimal(3));
            }
        }

        switch (request.getEmployment().getPosition()){
            case MIDDLE_MANAGER -> {
                rate.subtract(new BigDecimal(2));
            }
            case TOP_MANAGER -> {
                rate.subtract(new BigDecimal(4));
            }
        }

        if(request.getAmount().compareTo(request.getEmployment().getSalary().multiply(new BigDecimal(20)))>0)
            result=null;

        switch (request.getMaritalStatus()){
            case MARRIED -> {
                rate.subtract(new BigDecimal(3));
            }
            case DIVORCED -> {
                rate.add(new BigDecimal(1));
            }
        }

        int age = Period.between(request.getBirthDate(), LocalDate.now()).getYears();

        if(age<20 || age>60){
            result=null;
        }

        switch (request.getGender()){
            case MALE -> {
                if(age>=30 && age<=55)
                    rate.subtract(new BigDecimal(3));
            }
            case FEMALE -> {
                if(age>=35 && age<=60)
                    rate.subtract(new BigDecimal(3));
            }
            case NON_BINARY -> {
                rate.add(new BigDecimal(3));
            }
        }

        if(request.getEmployment().getWorkExperienceTotal()<12 || request.getEmployment().getWorkExperienceCurrent()<3)
            result=null;

        return result;
    }
}
