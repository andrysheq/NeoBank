package com.andrysheq.deal.repo;

import com.andrysheq.deal.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findById(Long id);

    Client save(Client client);

    void deleteById(Long id);
}

