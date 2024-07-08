package com.andrysheq.deal.repo.service;

import com.andrysheq.deal.entity.Credit;

public interface CreditRepoService {
    Credit findById(Long id);

    Credit save(Credit credit);

    Credit update(Credit credit);
}

