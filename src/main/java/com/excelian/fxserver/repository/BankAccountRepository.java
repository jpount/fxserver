package com.excelian.fxserver.repository;

import com.excelian.fxserver.domain.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the BankAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    @Query("select bank_account from BankAccount bank_account where bank_account.user.login = ?#{principal.username}")
    List<BankAccount> findByUserIsCurrentUser();

    @Query("select bank_account from BankAccount bank_account where bank_account.createdBy.login = ?#{principal.username}")
    List<BankAccount> findByCreatedByIsCurrentUser();

    @Query("select bank_account from BankAccount bank_account where bank_account.updatedBy.login = ?#{principal.username}")
    List<BankAccount> findByUpdatedByIsCurrentUser();

}
