package com.demo.user.aggregate;

import com.demo.user.command.RegisterUserCommand;
import com.demo.user.command.RemoveUserCommand;
import com.demo.user.command.UpdateUserCommand;
import com.demo.user.core.event.UserRegisteredEvent;
import com.demo.user.core.event.UserRemovedEvent;
import com.demo.user.core.event.UserUpdatedEvent;
import com.demo.user.core.model.User;
import com.demo.user.security.PasswordEncoder;
import com.demo.user.security.PasswordEncoderImpl;
import lombok.Getter;
import lombok.Setter;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

@Aggregate
@Getter
@Setter
public class UserAggregate {
    private final PasswordEncoder passwordEncoder;
    @AggregateIdentifier
    private String id;
    private User user;

    protected UserAggregate() {
        this.passwordEncoder = new PasswordEncoderImpl();
    }

    @CommandHandler
    public UserAggregate(RegisterUserCommand command) {
        var newUser = command.getUser();
        newUser.setId(command.getId());
        this.passwordEncoder = new PasswordEncoderImpl();
        var password = newUser.getAccount().getPassword();
        var hashedPassword = passwordEncoder.hashedPassword(password);
        newUser.getAccount().setPassword(hashedPassword);

        var event = UserRegisteredEvent.builder()
                .id(command.getId())
                .user(newUser)
                .build();

        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(UpdateUserCommand command) {
        var updatedUser = command.getUser();
        updatedUser.setId(command.getId());
        var password = updatedUser.getAccount().getPassword();
        var hashedPassword = passwordEncoder.hashedPassword(password);
        updatedUser.getAccount().setPassword(hashedPassword);

        var event = UserUpdatedEvent.builder()
                .id(UUID.randomUUID().toString())
                .user(updatedUser)
                .build();

        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(RemoveUserCommand command) {
        var event = new UserRemovedEvent(command.getId());
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(UserRegisteredEvent event) {
        this.id = event.getId();
        this.user = event.getUser();
    }

    @EventSourcingHandler
    public void on(UserUpdatedEvent event) {
        this.user = event.getUser();
    }

    @EventSourcingHandler
    public void on(UserRemovedEvent event) {
        AggregateLifecycle.markDeleted();
    }
}
