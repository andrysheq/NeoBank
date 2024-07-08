package com.andrysheq.deal.repo.service.impl;

import com.andrysheq.deal.entity.Client;
import com.andrysheq.deal.exception.RecordNotFoundException;
import com.andrysheq.deal.repo.ClientRepository;
import com.andrysheq.deal.repo.service.ClientRepoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
@RequiredArgsConstructor
@Log4j2
public class ClientRepoServiceImpl implements ClientRepoService {
    private final ClientRepository clientRepository;
    @Override
    @Transactional(readOnly = true)
    public Client findById(Long id) {
        Optional<Client> clientOpt = clientRepository.findById(id);

        return clientOpt
                .orElseThrow(() -> new RecordNotFoundException("Клиент не найден. id: " + id));
    }

    @Override
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Client update(Client client) {
        return clientRepository.saveAndFlush(client);
    }
}
