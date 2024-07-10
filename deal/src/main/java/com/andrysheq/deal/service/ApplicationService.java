package com.andrysheq.deal.service;

import com.andrysheq.deal.dto.FinishRegistrationRequestDTO;
import com.andrysheq.deal.dto.LoanApplicationRequestDTO;
import com.andrysheq.deal.entity.Application;
import com.andrysheq.deal.entity.Client;
import com.andrysheq.deal.enums.CreditStatus;
import com.andrysheq.deal.enums.Status;
import com.andrysheq.deal.mapper.BaseMapper;
import com.andrysheq.deal.repo.service.ApplicationRepoService;
import com.andrysheq.deal.repo.service.ClientRepoService;
import com.andrysheq.deal.repo.service.CreditRepoService;
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
    public ResponseEntity<String> signDocuments(Long applicationId){
        Application application = applicationRepoService.findById(applicationId);
        if(application.getCredit()!=null) {
            String sesCode = emailService.generateRandomCode();
            try {
                String signingLink = "http://localhost:8002/deal/document/" + applicationId + "/code?sesCode=" + sesCode;
                emailService.sendEmail(application.getClient().getEmail(), sesCode, signingLink);
                application.setSesCode(sesCode);
            } catch (MessagingException e) {
                e.printStackTrace();
                throw new RuntimeException("Ошибка отправки документов");
            }
            applicationRepoService.update(application);
        }else{
            throw new RuntimeException("Рассчитайте кредит в deal/calculate");
        }

        return ResponseEntity.ok("Документы отправлены на почту клиента");
    }

    public Application codeDocuments(Long applicationId, String sesCode){
        Application application = applicationRepoService.findById(applicationId);

        if(application.getSesCode().equals(sesCode)) {
            application.getCredit().setCreditStatus(CreditStatus.ISSUED);
            application.setStatus(Status.CREDIT_ISSUED);
            application.setSignDate(LocalDateTime.now());
            applicationRepoService.update(application);
        }else{
            throw new RuntimeException("Код подтверждения некорректен.");
        }

        return application;
    }
}
