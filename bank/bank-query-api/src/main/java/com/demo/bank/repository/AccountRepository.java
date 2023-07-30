package com.demo.bank.repository;

import com.demo.bank.core.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    List<Account> findByAccountHolderId(String accountHolderId);

    List<Account> findByBalanceGreaterThan(Double balance);
    
    List<Account> findByBalanceLessThan(Double balance);
}
