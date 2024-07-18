package com.andrysheq.deal.service;

import com.andrysheq.deal.dto.FinishRegistrationRequestDTO;
import com.andrysheq.deal.dto.LoanApplicationRequestDTO;
import com.andrysheq.deal.entity.ApplicationEntity;
import com.andrysheq.deal.entity.ClientEntity;
import com.andrysheq.deal.repo.service.ClientRepoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
@Service
public class ClientService {
    private final ClientRepoService clientRepoService;

    public ClientEntity getClient(LoanApplicationRequestDTO request){
        ClientEntity result = new ClientEntity();
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

    public ClientEntity finishRegistration(FinishRegistrationRequestDTO request, ApplicationEntity application){
        ClientEntity client = application.getClient();
        client.setGender(request.getGender());
        client.setAccount(request.getAccount());
        client.setEmployment(request.getEmployment());
        client.setDependentAmount(request.getDependentAmount());
        client.setIsSalaryClient(application.getLoanOffer().getIsSalaryClient());
        client.setIsInsuranceEnabled(application.getLoanOffer().getIsInsuranceEnabled());
        client.setMaritalStatus(request.getMaritalStatus());
        client.setPassportIssueDate(request.getPassportIssueDate());
        client.setPassportIssueBranch(request.getPassportIssueBranch());

        return clientRepoService.update(client);
    }

    public ClientEntity initClient(LoanApplicationRequestDTO request) {
        ClientEntity client = getClient(request);
        return clientRepoService.save(client);
    }
}
