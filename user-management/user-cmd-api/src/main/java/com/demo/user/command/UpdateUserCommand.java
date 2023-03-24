package com.demo.user.command;

import com.demo.user.core.model.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class UpdateUserCommand {
    @TargetAggregateIdentifier
    private String id;
    @Valid
    @NotNull(message = "Provide user details")
    private User user;
}
