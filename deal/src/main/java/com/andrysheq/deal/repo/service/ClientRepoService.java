package com.andrysheq.deal.repo.service;

import com.andrysheq.deal.entity.ClientEntity;

public interface ClientRepoService {
    ClientEntity findById(Long id);

    ClientEntity save(ClientEntity client);

    ClientEntity update(ClientEntity client);
}

