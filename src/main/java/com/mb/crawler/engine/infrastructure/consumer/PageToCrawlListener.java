package com.mb.crawler.engine.infrastructure.consumer;

import com.mb.crawler.events.PageToCrawlEvent;
import com.mb.crawler.engine.api.CrawlerApi;
import com.mb.crawler.events.CrawlResultEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class PageToCrawlListener {
    private final CrawlerApi crawlerApi;
    private final ApplicationEventPublisher eventPublisher;


    @EventListener
    public void on(PageToCrawlEvent event) {
        try {
            var result = crawlerApi.crawl(event.url());

            if (result.isPresent()) {
                log.debug("Page crawl result {}", result);
                eventPublisher.publishEvent(new CrawlResultEvent(result.get().originLink(), result.get().links()));
            } else {
                log.debug("Empty result");
            }

        } catch (Exception e) {
            log.warn("Could not process event: {}", event, e);
        }
    }
}
