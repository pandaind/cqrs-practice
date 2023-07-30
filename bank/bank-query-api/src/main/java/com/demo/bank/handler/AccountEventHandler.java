package com.demo.bank.handler;

import com.demo.bank.core.event.AccountClosedEvent;
import com.demo.bank.core.event.AccountOpenedEvent;
import com.demo.bank.core.event.FundsDepositedEvent;
import com.demo.bank.core.event.FundsWithdrawnEvent;

public interface AccountEventHandler {
    void on(AccountOpenedEvent event);

    void on(FundsDepositedEvent event);

    void on(FundsWithdrawnEvent event);

    void on(AccountClosedEvent event);
}
