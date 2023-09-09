package com.demo.account.api.command;

import com.demo.cqrs.command.BaseCommand;
import lombok.Data;

@Data
public class CloseAccountCommand extends BaseCommand {
    public CloseAccountCommand(String id){
        super(id);
    }
}
