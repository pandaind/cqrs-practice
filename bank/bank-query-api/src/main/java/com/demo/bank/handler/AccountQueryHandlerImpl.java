package com.demo.bank.handler;

import com.demo.bank.dto.AccountLookupResponse;
import com.demo.bank.dto.EqualityType;
import com.demo.bank.query.FindAccountByHolderId;
import com.demo.bank.query.FindAccountByIdQuery;
import com.demo.bank.query.FindAccountWithBalanceQuery;
import com.demo.bank.query.FindAllAccountQuery;
import com.demo.bank.repository.AccountRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountQueryHandlerImpl implements AccountQueryHandler {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountQueryHandlerImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @QueryHandler
    @Override
    public AccountLookupResponse findAccountById(FindAccountByIdQuery query) {
        var account = accountRepository.findById(query.getId());
        return account.map(value -> new AccountLookupResponse(value, "Bank account successfully found."))
                .orElseGet(() -> new AccountLookupResponse("No Bank account found for id " + query.getId()));
    }

    @QueryHandler
    @Override
    public AccountLookupResponse findAccountByHolderId(FindAccountByHolderId query) {
        var accounts = accountRepository.findByAccountHolderId(query.getAccountHolderId());
        return accounts.isEmpty()
                ? new AccountLookupResponse("No Bank account found for Holder id " + query.getAccountHolderId())
                : new AccountLookupResponse(accounts, "Found " + accounts.size() + " bank account[s]");
    }

    @QueryHandler
    @Override
    public AccountLookupResponse findAllAccounts(FindAllAccountQuery query) {
        var allAccounts = accountRepository.findAll();
        return allAccounts.isEmpty()
                ? new AccountLookupResponse("No Bank accounts found!")
                : new AccountLookupResponse(allAccounts, "Found " + allAccounts.size() + " bank account[s]");
    }

    @QueryHandler
    @Override
    public AccountLookupResponse findAccountWithBalance(FindAccountWithBalanceQuery query) {
        var accounts = query.getEqualityType().equals(EqualityType.GREATER_THAN)
                ? accountRepository.findByBalanceGreaterThan(query.getBalance())
                : accountRepository.findByBalanceLessThan(query.getBalance());

        return accounts.isEmpty()
                ? new AccountLookupResponse("No Bank accounts found!")
                : new AccountLookupResponse(accounts, "Found " + accounts.size() + " bank account[s]");
    }
}
