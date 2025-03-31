package org.veedev.accountservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.veedev.accountservice.model.Account;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByClientId(Long clientId);
    Account findByNumber(String number);
}
