package com.andrysheq.deal.service;

import com.andrysheq.deal.dto.ApplicationStatusHistoryDTO;
import com.andrysheq.deal.dto.FinishRegistrationRequestDTO;
import com.andrysheq.deal.dto.LoanOfferDTO;
import com.andrysheq.deal.dto.ScoringDataDTO;
import com.andrysheq.deal.entity.*;
import com.andrysheq.deal.enums.ChangeType;
import com.andrysheq.deal.enums.CreditStatus;
import com.andrysheq.deal.enums.Status;
import com.andrysheq.deal.feign.DealFeignClient;
import com.andrysheq.deal.mapper.BaseMapper;
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
    private final BaseMapper mapper;


    public ApplicationEntity initApplication(ClientEntity client){
        ApplicationEntity application = new ApplicationEntity(client);
        ApplicationStatusHistoryDTO statusHistoryDTO = new ApplicationStatusHistoryDTO();
        statusHistoryDTO.setStatus(Status.PREAPPROVAL);
        statusHistoryDTO.setChangeType(ChangeType.APPROVED);
        statusHistoryDTO.setTime(LocalDateTime.now());
        ApplicationStatusHistoryEntity statusHistory = mapper.map(statusHistoryDTO, ApplicationStatusHistoryEntity.class);
        application.addStatusHistory(statusHistory);
        return applicationRepoService.save(application);
    }

    public ApplicationEntity finishApplication(FinishRegistrationRequestDTO request, Long applicationId){
        ApplicationEntity application = applicationRepoService.findById(applicationId);

        CreditEntity credit = creditService.initCredit(dealFeignClient.scoring(new ScoringDataDTO(request,application)));

        application.setStatus(Status.CC_APPROVED);
        application.setCredit(credit);

        application.setClient(clientService.finishRegistration(request,application));

        ApplicationStatusHistoryDTO statusHistoryDTO = new ApplicationStatusHistoryDTO();
        statusHistoryDTO.setTime(LocalDateTime.now());
        statusHistoryDTO.setStatus(Status.CC_APPROVED);
        statusHistoryDTO.setChangeType(ChangeType.APPROVED);

        ApplicationStatusHistoryEntity statusHistory = mapper.map(statusHistoryDTO, ApplicationStatusHistoryEntity.class);
        application.addStatusHistory(statusHistory);

        return applicationRepoService.update(application);
    }

    public ApplicationEntity doOffer(LoanOfferDTO request){
        ApplicationEntity application = applicationRepoService.findById(request.getApplicationId());
        
        application.setLoanOffer(mapper.map(request, LoanOfferEntity.class));
        application.setStatus(Status.PREAPPROVAL);

        return applicationRepoService.update(application);
    }
    public ResponseEntity<String> signDocuments(Long applicationId){
        ApplicationEntity application = applicationRepoService.findById(applicationId);
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
                application.addStatusHistory(mapper.map(statusHistoryDTO, ApplicationStatusHistoryEntity.class));

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

    public ApplicationEntity codeDocuments(Long applicationId, String sesCode){
        ApplicationEntity application = applicationRepoService.findById(applicationId);

        if(application.getSesCode().equals(sesCode)) {
            ApplicationStatusHistoryDTO statusHistoryDTO = new ApplicationStatusHistoryDTO();
            statusHistoryDTO.setTime(LocalDateTime.now());
            statusHistoryDTO.setStatus(Status.DOCUMENT_SIGNED);
            statusHistoryDTO.setChangeType(ChangeType.APPROVED);
            application.addStatusHistory(mapper.map(statusHistoryDTO, ApplicationStatusHistoryEntity.class));
            application.setSignDate(LocalDateTime.now());

            application.getCredit().setCreditStatus(CreditStatus.ISSUED);
            application.setStatus(Status.CREDIT_ISSUED);

            ApplicationStatusHistoryDTO creditIssuedStatus = new ApplicationStatusHistoryDTO();
            creditIssuedStatus.setTime(LocalDateTime.now());
            creditIssuedStatus.setStatus(Status.CREDIT_ISSUED);
            creditIssuedStatus.setChangeType(ChangeType.APPROVED);
            application.addStatusHistory(mapper.map(creditIssuedStatus,ApplicationStatusHistoryEntity.class));

            applicationRepoService.update(application);
        }else{
            throw new RuntimeException("Код подтверждения некорректен.");
        }

        return application;
    }
}
