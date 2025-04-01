package org.veedev.reportservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.veedev.reportservice.model.Transaction;

import java.util.List;

@Repository
public interface ReportRepository extends MongoRepository<Transaction, String> {
    List<Transaction> findByNumberAccount(String numberAccount);
}
