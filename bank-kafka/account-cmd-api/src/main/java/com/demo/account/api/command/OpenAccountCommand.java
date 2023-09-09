package com.demo.account.api.command;

import com.demo.account.dto.AccountType;
import com.demo.cqrs.command.BaseCommand;
import lombok.Data;

@Data
public class OpenAccountCommand extends BaseCommand {
    private String accountHolder;
    private AccountType accountType;
    private double openingBalance;
}
