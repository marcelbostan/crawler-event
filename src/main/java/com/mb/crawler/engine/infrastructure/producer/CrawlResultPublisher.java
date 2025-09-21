package com.mb.crawler.engine.infrastructure.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CrawlResultPublisher {
    private final ApplicationEventPublisher eventPublisher;

    public void publish(CrawlResultEvent result) {
        eventPublisher.publishEvent(result);
    }
}
