package org.veedev.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.veedev.authservice.model.Client;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Boolean existsByEmail(String email);
    Boolean existsByPhoneNumber(String phoneNumber);
    Client findByPhoneNumber(String phoneNumber);
    Optional<Client> getClientByPhoneNumber(String phoneNumber);
}
