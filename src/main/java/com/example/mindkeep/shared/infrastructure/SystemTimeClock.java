package com.example.mindkeep.shared.infrastructure;

import com.example.mindkeep.shared.domain.Clock;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SystemTimeClock implements Clock {

    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}

