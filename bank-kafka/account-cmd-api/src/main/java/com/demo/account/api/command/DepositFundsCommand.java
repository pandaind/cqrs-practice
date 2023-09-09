package com.demo.account.api.command;

import com.demo.cqrs.command.BaseCommand;
import lombok.Data;

@Data
public class DepositFundsCommand extends BaseCommand {
    private double amount;
}
