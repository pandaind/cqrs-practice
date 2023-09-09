package com.demo.account.api.command;

import com.demo.cqrs.command.BaseCommand;
import lombok.Data;

@Data
public class WithdrawFundsCommand extends BaseCommand {
    private double amount;
}
