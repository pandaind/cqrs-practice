package com.demo.bank.command;

import com.demo.bank.core.model.AccountType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class OpenAccountCommand {
    @TargetAggregateIdentifier
    private String id;

    @Valid
    @NotNull(message = "No account holder id is supplied.")
    private String accountHolderId;

    @Valid
    @NotNull(message = "No account type is supplied.")
    private AccountType accountType;

    @Valid
    @Min(value = 50, message = "Opening balance must be 50.")
    private Double openingBalance;

}
