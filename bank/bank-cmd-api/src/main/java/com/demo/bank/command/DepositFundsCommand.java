package com.demo.bank.command;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class DepositFundsCommand {
    @TargetAggregateIdentifier
    private String id;
    @Valid
    @Min(value = 1, message = "Amount should be greater than 0.")
    private Double amount;
}