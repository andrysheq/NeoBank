package com.andrysheq.deal.repo.service.impl;

import com.andrysheq.deal.exception.RecordNotFoundException;
import com.andrysheq.deal.entity.Credit;
import com.andrysheq.deal.repo.CreditRepository;
import com.andrysheq.deal.repo.service.CreditRepoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class CreditRepoServiceImpl implements CreditRepoService {
    private final CreditRepository creditRepository;
    @Override
    @Transactional(readOnly = true)
    public Credit findById(Long id) {
        Optional<Credit> creditOpt = creditRepository.findById(id);

        return creditOpt
                .orElseThrow(() -> new RecordNotFoundException("Кредит не найден. id: " + id));
    }

    @Override
    public Credit save(Credit credit) {
        return creditRepository.save(credit);
    }

    @Override
    public Credit update(Credit credit) {
        return creditRepository.saveAndFlush(credit);
    }
}
