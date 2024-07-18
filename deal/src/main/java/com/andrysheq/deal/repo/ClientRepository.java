package com.andrysheq.deal.repo;

import com.andrysheq.deal.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {

    Optional<ClientEntity> findById(Long id);

    ClientEntity save(ClientEntity client);

    void deleteById(Long id);
}

