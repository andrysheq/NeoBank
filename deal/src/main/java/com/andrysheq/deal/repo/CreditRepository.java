package com.andrysheq.deal.repo;

import com.andrysheq.deal.entity.Client;
import com.andrysheq.deal.entity.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CreditRepository extends JpaRepository<Credit, Long> {

    Optional<Credit> findById(Long id);

    Credit save(Credit credit);

    void deleteById(Long id);
}

