package com.example.mindkeep.shared.application;

import com.example.mindkeep.shared.domain.events.DomainEvent;
import com.example.mindkeep.shared.domain.events.EventBus;

import java.util.List;

public abstract class EventPublisher {

    private final EventBus eventBus;

    protected EventPublisher(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    protected void publishEvents(List<DomainEvent> events) {
        eventBus.publish(events);
    }

    protected void publishEvent(DomainEvent event) {
        eventBus.publish(event);
    }
}
