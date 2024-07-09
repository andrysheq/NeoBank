package com.andrysheq.deal.service;

import com.andrysheq.deal.dto.LoanApplicationRequestDTO;
import com.andrysheq.deal.entity.Client;

public class ApplicationService {
    public Client getClient(LoanApplicationRequestDTO request){
        Client result = new Client();
        result.setAmount(request.getAmount());
        result.setTerm(request.getTerm());
        result.setBirthDate(request.getBirthDate());
        result.setFirstName(request.getFirstName());
        result.setLastName(request.getLastName());
        result.setMiddleName(request.getMiddleName());
        result.setPassportNumber(result.getPassportNumber());
        result.setPassportSeries(request.getPassportSeries());

        return result;
    }
}
