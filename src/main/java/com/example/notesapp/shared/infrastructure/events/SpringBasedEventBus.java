package com.example.notesapp.shared.infrastructure.events;

import com.example.notesapp.shared.domain.events.DomainEvent;
import com.example.notesapp.shared.domain.events.EventBus;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpringBasedEventBus implements EventBus {

    private final ApplicationEventPublisher eventPublisher;

    public SpringBasedEventBus(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void publish(List<? extends DomainEvent> events) {
        events.forEach(this::publish);
    }

    @Override
    public void publish(DomainEvent... events) {
        publish(List.of(events));
    }

    private void publish(DomainEvent event) {
        eventPublisher.publishEvent(event);
    }
}
