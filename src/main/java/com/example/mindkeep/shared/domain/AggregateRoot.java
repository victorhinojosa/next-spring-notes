package com.example.mindkeep.shared.domain;

import com.example.mindkeep.shared.domain.events.DomainEvent;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(exclude = "domainEvents")
public abstract class AggregateRoot<T> implements Identifiable<T> {

    private List<DomainEvent> domainEvents = new ArrayList<>();

    public final List<DomainEvent> pullDomainEvents() {
        List<DomainEvent> events = domainEvents;
        domainEvents = new ArrayList<>();
        return events;
    }

    protected final void record(DomainEvent event) {
        domainEvents.add(event);
    }
}
