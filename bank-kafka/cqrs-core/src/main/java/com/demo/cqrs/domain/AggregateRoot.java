package com.demo.cqrs.domain;

import com.demo.cqrs.event.BaseEvent;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AggregateRoot {
    @Getter
    @Setter
    protected String id;
    @Getter
    @Setter
    private int version = -1;
    private final List<BaseEvent> changes = new ArrayList<>();

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private List<BaseEvent> getUncommittedChanges() {
        return this.changes;
    }

    public void markChangesAsCommitted() {
        this.changes.clear();
    }

    protected void applyChanges(BaseEvent event, Boolean isNewEvent) {
        try {
            var method = getClass().getDeclaredMethod("apply", event.getClass());
            method.setAccessible(true);
            method.invoke(this, event);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            logger.log(Level.WARNING, MessageFormat.format("The apply method was not found in aggregate for {0}", event.getClass().getName()));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error applying event to aggregate", e);
        } finally {
            if (isNewEvent) {
                changes.add(event);
            }
        }
    }

    public void raiseEvent(BaseEvent event) {
        applyChanges(event, true);
    }

    public void replayEvent(Iterable<BaseEvent> events) {
        events.forEach(event -> applyChanges(event, false));
    }
}
