package com.andrysheq.deal.repo;

import com.andrysheq.deal.entity.Application;
import com.andrysheq.deal.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    Optional<Application> findById(Long id);

    Application save(Application application);

    void deleteById(Long id);
}

