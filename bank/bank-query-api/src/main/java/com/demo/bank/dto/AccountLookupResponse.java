package com.demo.bank.dto;

import com.demo.bank.core.dto.BaseResponse;
import com.demo.bank.core.model.Account;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AccountLookupResponse extends BaseResponse {
    private List<Account> accounts;

    public AccountLookupResponse(String message) {
        super(message);
    }

    public AccountLookupResponse(List<Account> accounts, String message) {
        super(message);
        this.accounts = accounts;
    }

    public AccountLookupResponse(Account account, String message) {
        super(message);
        this.accounts = new ArrayList<>();
        this.accounts.add(account);
    }
}
