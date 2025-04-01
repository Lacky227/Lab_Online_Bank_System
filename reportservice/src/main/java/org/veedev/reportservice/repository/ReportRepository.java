package org.veedev.reportservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.veedev.reportservice.model.Transaction;

@Repository
public interface ReportRepository extends MongoRepository<Transaction, String> {
}
