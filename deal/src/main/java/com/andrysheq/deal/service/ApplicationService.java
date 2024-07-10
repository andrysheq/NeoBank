package com.andrysheq.deal.service;

import com.andrysheq.deal.dto.ApplicationStatusHistoryDTO;
import com.andrysheq.deal.dto.FinishRegistrationRequestDTO;
import com.andrysheq.deal.dto.LoanOfferDTO;
import com.andrysheq.deal.dto.ScoringDataDTO;
import com.andrysheq.deal.entity.Application;
import com.andrysheq.deal.entity.Client;
import com.andrysheq.deal.entity.Credit;
import com.andrysheq.deal.enums.ChangeType;
import com.andrysheq.deal.enums.CreditStatus;
import com.andrysheq.deal.enums.Status;
import com.andrysheq.deal.feign.DealFeignClient;
import com.andrysheq.deal.repo.service.ApplicationRepoService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
@Service
public class ApplicationService {
    private final ApplicationRepoService applicationRepoService;
    private final EmailService emailService;
    private final ClientService clientService;
    private final DealFeignClient dealFeignClient;
    private final CreditService creditService;


    public Application initApplication(Client client){
        Application application = new Application(client);
        ApplicationStatusHistoryDTO statusHistory = new ApplicationStatusHistoryDTO();
        statusHistory.setStatus(Status.PREAPPROVAL);
        statusHistory.setChangeType(ChangeType.APPROVED);
        statusHistory.setTime(LocalDateTime.now());
        application.addStatusHistory(statusHistory);
        return applicationRepoService.save(application);
    }

    public Application finishApplication(FinishRegistrationRequestDTO request, Long applicationId){
        Application application = applicationRepoService.findById(applicationId);

        Credit credit = creditService.initCredit(dealFeignClient.scoring(new ScoringDataDTO(request,application)));

        application.setStatus(Status.CC_APPROVED);
        application.setCredit(credit);

        application.setClient(clientService.finishRegistration(request,application));

        ApplicationStatusHistoryDTO statusHistoryDTO = new ApplicationStatusHistoryDTO();
        statusHistoryDTO.setTime(LocalDateTime.now());
        statusHistoryDTO.setStatus(Status.CC_APPROVED);
        statusHistoryDTO.setChangeType(ChangeType.APPROVED);
        application.addStatusHistory(statusHistoryDTO);

        return applicationRepoService.update(application);
    }

    public Application doOffer(LoanOfferDTO request){
        Application application = applicationRepoService.findById(request.getApplicationId());

        application.setLoanOffer(request);
        application.setStatus(Status.PREAPPROVAL);

        return applicationRepoService.update(application);
    }
    public ResponseEntity<String> signDocuments(Long applicationId){
        Application application = applicationRepoService.findById(applicationId);
        if(application.getCredit()!=null) {
            String sesCode = emailService.generateRandomCode();
            try {
                String signingLink = "http://localhost:8002/deal/document/" + applicationId + "/code?sesCode=" + sesCode;
                emailService.sendEmail(application.getClient().getEmail(), sesCode, signingLink);
                application.setSesCode(sesCode);

                ApplicationStatusHistoryDTO statusHistoryDTO = new ApplicationStatusHistoryDTO();
                statusHistoryDTO.setTime(LocalDateTime.now());
                statusHistoryDTO.setStatus(Status.DOCUMENT_CREATED);
                statusHistoryDTO.setChangeType(ChangeType.APPROVED);
                application.addStatusHistory(statusHistoryDTO);

                applicationRepoService.update(application);
            } catch (MessagingException e) {
                e.printStackTrace();
                throw new RuntimeException("Ошибка отправки документов");
            }
        }else{
            throw new RuntimeException("Рассчитайте кредит в deal/calculate");
        }

        return ResponseEntity.ok("Документы отправлены на почту клиента");
    }

    public Application codeDocuments(Long applicationId, String sesCode){
        Application application = applicationRepoService.findById(applicationId);

        if(application.getSesCode().equals(sesCode)) {
            ApplicationStatusHistoryDTO statusHistoryDTO = new ApplicationStatusHistoryDTO();
            statusHistoryDTO.setTime(LocalDateTime.now());
            statusHistoryDTO.setStatus(Status.DOCUMENT_SIGNED);
            statusHistoryDTO.setChangeType(ChangeType.APPROVED);
            application.addStatusHistory(statusHistoryDTO);
            application.setSignDate(LocalDateTime.now());

            application.getCredit().setCreditStatus(CreditStatus.ISSUED);
            application.setStatus(Status.CREDIT_ISSUED);

            ApplicationStatusHistoryDTO creditIssuedStatus = new ApplicationStatusHistoryDTO();
            creditIssuedStatus.setTime(LocalDateTime.now());
            creditIssuedStatus.setStatus(Status.CREDIT_ISSUED);
            creditIssuedStatus.setChangeType(ChangeType.APPROVED);
            application.addStatusHistory(creditIssuedStatus);

            applicationRepoService.update(application);
        }else{
            throw new RuntimeException("Код подтверждения некорректен.");
        }

        return application;
    }
}
