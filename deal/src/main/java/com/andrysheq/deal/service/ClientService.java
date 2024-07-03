package com.andrysheq.deal.service;

import com.andrysheq.conveyor.dto.CreditDTO;
import com.andrysheq.conveyor.dto.LoanApplicationRequestDTO;
import com.andrysheq.conveyor.dto.PaymentScheduleElement;
import com.andrysheq.conveyor.dto.ScoringDataDTO;
import com.andrysheq.deal.entity.Client;

import java.math.BigDecimal;
import java.util.List;

public class ClientService {
    public Client getClient(LoanApplicationRequestDTO request){
        Client result = new Client();
        result.setAmount(request.getAmount());
        result.setTerm(request.getTerm());
        //result.setIsSalaryClient();
        result.setBirthDate(request.getBirthDate());
        //result.setDependentAmount();
        result.setFirstName(request.getFirstName());
        result.setLastName(request.getLastName());
        result.setMiddleName(request.getMiddleName());
        //result.setMaritalStatus(request.get);
        result.setPassportNumber(result.getPassportNumber());
        result.setPassportSeries(request.getPassportSeries());

        return result;
    }
}
