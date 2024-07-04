package com.andrysheq.deal.repo.service;

import com.andrysheq.deal.entity.Application;

public interface ApplicationRepoService {
    Application findById(Long id);

    Application save(Application application);

    Application update(Application application);
}