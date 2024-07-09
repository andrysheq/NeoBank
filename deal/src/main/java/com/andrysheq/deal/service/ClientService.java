package com.andrysheq.deal.service;

import com.andrysheq.deal.dto.LoanApplicationRequestDTO;
import com.andrysheq.deal.entity.Client;
import org.springframework.stereotype.Service;
@Service
public class ClientService {
    public Client getClient(LoanApplicationRequestDTO request){
        Client result = new Client();
        result.setAmount(request.getAmount());
        result.setTerm(request.getTerm());
        result.setBirthDate(request.getBirthDate());
        result.setFirstName(request.getFirstName());
        result.setLastName(request.getLastName());
        result.setMiddleName(request.getMiddleName());
        result.setPassportNumber(request.getPassportNumber());
        result.setPassportSeries(request.getPassportSeries());
        result.setEmail(request.getEmail());

        return result;
    }
}
