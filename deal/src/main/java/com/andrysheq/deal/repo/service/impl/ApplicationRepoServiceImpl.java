package com.andrysheq.deal.repo.service.impl;

import com.andrysheq.deal.exception.RecordNotFoundException;
import com.andrysheq.deal.entity.Application;
import com.andrysheq.deal.repo.ApplicationRepository;
import com.andrysheq.deal.repo.service.ApplicationRepoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
@RequiredArgsConstructor
@Log4j2
public class ApplicationRepoServiceImpl implements ApplicationRepoService {
    private final ApplicationRepository applicationRepository;
    @Override
    @Transactional(readOnly = true)
    public Application findById(Long id) {
        Optional<Application> clientOpt = applicationRepository.findById(id);

        return clientOpt
                .orElseThrow(() -> new RecordNotFoundException("Клиент не найден. id: " + id));
    }

    @Override
    public Application save(Application application) {
        return applicationRepository.save(application);
    }

    @Override
    public Application update(Application application) {
        return applicationRepository.saveAndFlush(application);
    }
}
