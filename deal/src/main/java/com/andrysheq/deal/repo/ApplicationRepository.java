package com.andrysheq.deal.repo;

import com.andrysheq.deal.entity.ApplicationEntity;
import com.andrysheq.deal.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<ApplicationEntity, Long> {

    Optional<ApplicationEntity> findById(Long id);

    ApplicationEntity save(ApplicationEntity application);

    void deleteById(Long id);
}

