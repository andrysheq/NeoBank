package com.andrysheq.deal.repo.service;

import com.andrysheq.deal.entity.Client;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

public interface ClientRepoService {
    Client findById(Long id);

    Client save(Client client);

    Client update(Client client);
}

