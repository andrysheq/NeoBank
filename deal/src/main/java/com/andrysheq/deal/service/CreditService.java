package com.andrysheq.deal.service;

import com.andrysheq.deal.dto.CreditDTO;
import com.andrysheq.deal.entity.Credit;
import com.andrysheq.deal.enums.CreditStatus;
import com.andrysheq.deal.mapper.BaseMapper;
import com.andrysheq.deal.repo.service.CreditRepoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
@Service
public class CreditService {
    private final CreditRepoService creditRepoService;
    private final BaseMapper mapper;

    public Credit initCredit(CreditDTO creditDTO){
        Credit credit = mapper.map(creditDTO, Credit.class);
        credit.setCreditStatus(CreditStatus.CALCULATED);

        return creditRepoService.save(credit);
    }
}
