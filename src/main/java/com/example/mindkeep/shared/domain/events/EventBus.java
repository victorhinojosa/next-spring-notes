package com.example.mindkeep.shared.domain.events;

import java.util.List;

public interface EventBus {

    void publish(List<? extends DomainEvent> events);

    void publish(DomainEvent... events);
}

