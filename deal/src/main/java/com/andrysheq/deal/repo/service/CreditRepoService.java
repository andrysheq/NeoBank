package com.andrysheq.deal.repo.service;

import com.andrysheq.deal.entity.CreditEntity;

public interface CreditRepoService {
    CreditEntity findById(Long id);

    CreditEntity save(CreditEntity credit);

    CreditEntity update(CreditEntity credit);
}

