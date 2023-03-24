package com.demo.user.handler;

import com.demo.user.core.event.UserRegisteredEvent;
import com.demo.user.core.event.UserRemovedEvent;
import com.demo.user.core.event.UserUpdatedEvent;
import com.demo.user.repository.UserRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@ProcessingGroup("user-group")
public class UserEventHandlerImpl implements UserEventHandler {

    private final UserRepository repository;

    @Autowired
    public UserEventHandlerImpl(UserRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    @Override
    public void on(UserRegisteredEvent event) {
        repository.save(event.getUser());
    }

    @EventHandler
    @Override
    public void on(UserUpdatedEvent event) {
        repository.save(event.getUser());
    }

    @EventHandler
    @Override
    public void on(UserRemovedEvent event) {
        repository.deleteById(event.getId());
    }
}
