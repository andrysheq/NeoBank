package com.andrysheq.deal.repo.service;

import com.andrysheq.deal.entity.Client;

public interface ClientRepoService {
    Client findById(Long id);

    Client save(Client client);

    Client update(Client client);
}

