package com.demo.account.infrastructure;

import com.demo.cqrs.command.BaseCommand;
import com.demo.cqrs.command.CommandHandlerMethod;
import com.demo.cqrs.infrastructure.CommandDispatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class AccountCommandDispatcher implements CommandDispatcher {
    private final Map<Class<? extends BaseCommand>, List<CommandHandlerMethod>> routes = new HashMap<>();

    @Override
    public <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler) {
        var handlers = routes.computeIfAbsent(type, c -> new LinkedList<>());
        handlers.add(handler);
    }

    @Override
    public void send(BaseCommand command) {
        var handlers = routes.get(command.getClass());

        if (CollectionUtils.isEmpty(handlers)) {
            throw new RuntimeException("No command handler has registered!");
        }

        if (handlers.size() > 1) {
            throw new RuntimeException("Cannot send command to more than one handler!");
        }

        handlers.get(0).handle(command);
    }
}
