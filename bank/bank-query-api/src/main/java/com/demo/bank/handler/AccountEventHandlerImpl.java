package com.demo.bank.handler;

import com.demo.bank.core.event.AccountClosedEvent;
import com.demo.bank.core.event.AccountOpenedEvent;
import com.demo.bank.core.event.FundsDepositedEvent;
import com.demo.bank.core.event.FundsWithdrawnEvent;
import com.demo.bank.core.model.Account;
import com.demo.bank.repository.AccountRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@ProcessingGroup("bank-group")
public class AccountEventHandlerImpl implements AccountEventHandler {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountEventHandlerImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @EventHandler
    @Override
    public void on(AccountOpenedEvent event) {
        var account = Account.builder()
                .id(event.getId())
                .accountHolderId(event.getAccountHolderId())
                .accountType(event.getAccountType())
                .balance(event.getOpeningBalance())
                .createdDate(event.getCreationDate())
                .build();
        accountRepository.save(account);
    }

    @EventHandler
    @Override
    public void on(FundsDepositedEvent event) {
        var account = accountRepository.findById(event.getId());

        account.ifPresent(a -> {
            a.setBalance(event.getBalance());
            accountRepository.save(a);
        });
    }

    @EventHandler
    @Override
    public void on(FundsWithdrawnEvent event) {
        var account = accountRepository.findById(event.getId());

        account.ifPresent(a -> {
            a.setBalance(event.getBalance());
            accountRepository.save(a);
        });
    }

    @EventHandler
    @Override
    public void on(AccountClosedEvent event) {
        accountRepository.deleteById(event.getId());
    }
}
