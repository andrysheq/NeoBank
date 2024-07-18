package com.andrysheq.deal.repo.service;

import com.andrysheq.deal.entity.ApplicationEntity;

public interface ApplicationRepoService {
    ApplicationEntity findById(Long id);

    ApplicationEntity save(ApplicationEntity application);

    ApplicationEntity update(ApplicationEntity application);
}