package com.example.notesapp.shared.infrastructure;

import com.example.notesapp.shared.domain.Clock;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SystemTimeClock implements Clock {

    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}

