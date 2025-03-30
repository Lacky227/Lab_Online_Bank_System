package org.veedev.accountservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.veedev.accountservice.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByClientId(Long clientId);
}
