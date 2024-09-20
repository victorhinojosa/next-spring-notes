package com.example.mindkeep.shared.infrastructure;

import com.example.mindkeep.shared.application.DomainRetriever;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ExternalPropertiesDomainRetriever implements DomainRetriever {

    private final String domain;

    public ExternalPropertiesDomainRetriever(@Value("${app.domain}") String domain) {
        if (domain == null) {
            throw new IllegalArgumentException("Domain cannot be null");
        }
        this.domain = domain;

        log.info("DomainRetriever initialized with domain: {}", domain);
    }

    @Override
    public String getDomain() {
        return domain;
    }
}

