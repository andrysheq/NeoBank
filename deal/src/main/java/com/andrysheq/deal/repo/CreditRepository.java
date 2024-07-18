package com.andrysheq.deal.repo;

import com.andrysheq.deal.entity.CreditEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CreditRepository extends JpaRepository<CreditEntity, Long> {

    Optional<CreditEntity> findById(Long id);

    CreditEntity save(CreditEntity credit);

    void deleteById(Long id);
}

