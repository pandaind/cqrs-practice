package com.demo.bank.handler;

import com.demo.bank.dto.AccountLookupResponse;
import com.demo.bank.query.FindAccountByHolderId;
import com.demo.bank.query.FindAccountByIdQuery;
import com.demo.bank.query.FindAccountWithBalanceQuery;
import com.demo.bank.query.FindAllAccountQuery;

public interface AccountQueryHandler {
    AccountLookupResponse findAccountById(FindAccountByIdQuery query);
    AccountLookupResponse findAccountByHolderId(FindAccountByHolderId query);
    AccountLookupResponse findAllAccounts(FindAllAccountQuery query);
    AccountLookupResponse findAccountWithBalance(FindAccountWithBalanceQuery query);
}
