package com.excelian.fxserver.repository;

import com.excelian.fxserver.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Transaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("select transaction from Transaction transaction where transaction.createdBy.login = ?#{principal.username}")
    List<Transaction> findByCreatedByIsCurrentUser();

    @Query("select transaction from Transaction transaction where transaction.updatedBy.login = ?#{principal.username}")
    List<Transaction> findByUpdatedByIsCurrentUser();

}
