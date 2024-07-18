package com.andrysheq.deal.repo.service.impl;

import com.andrysheq.deal.entity.ApplicationEntity;
import com.andrysheq.deal.exception.RecordNotFoundException;
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
    public ApplicationEntity findById(Long id) {
        Optional<ApplicationEntity> clientOpt = applicationRepository.findById(id);

        return clientOpt
                .orElseThrow(() -> new RecordNotFoundException("Заявка не найдена. id: " + id));
    }

    @Override
    public ApplicationEntity save(ApplicationEntity application) {
        return applicationRepository.save(application);
    }

    @Override
    public ApplicationEntity update(ApplicationEntity application) {
        return applicationRepository.saveAndFlush(application);
    }
}
