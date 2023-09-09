package com.demo.cqrs.infrastructure;

import com.demo.cqrs.command.BaseCommand;
import com.demo.cqrs.command.CommandHandlerMethod;

public interface CommandDispatcher {
    <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler);

    void send(BaseCommand command);
}
