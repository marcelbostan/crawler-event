package com.mb.crawler.engine.infrastructure.consumer;

import com.mb.crawler.engine.api.CrawlerApi;
import com.mb.crawler.engine.api.PageToCrawl;
import com.mb.crawler.engine.infrastructure.producer.CrawlResultEvent;
import com.mb.crawler.engine.infrastructure.producer.CrawlResultPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class PageToCrawlListener {
    private final CrawlerApi crawlerApi;
    private final CrawlResultPublisher crawlResultPublisher;


    @EventListener
    public void on(PageToCrawlEvent event) {
        try {
            var result = crawlerApi.crawl(new PageToCrawl(event.url()));

            if (result.isPresent()) {
                log.debug("Page crawl result {}", result);
                crawlResultPublisher.publish(new CrawlResultEvent(result.get().originLink(), result.get().links()));
            } else {
                log.debug("Empty result");
            }

        } catch (Exception e) {
            log.warn("Could not process event: {}", event, e);
        }
    }
}
