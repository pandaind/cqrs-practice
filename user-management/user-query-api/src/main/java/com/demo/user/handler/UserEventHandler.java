package com.demo.user.handler;

import com.demo.user.core.event.UserRegisteredEvent;
import com.demo.user.core.event.UserRemovedEvent;
import com.demo.user.core.event.UserUpdatedEvent;

public interface UserEventHandler {
    void on(UserRegisteredEvent event);

    void on(UserUpdatedEvent event);

    void on(UserRemovedEvent event);
}
