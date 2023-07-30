package com.demo.bank.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class CloseAccountCommand {
    @TargetAggregateIdentifier
    private String id;
}
