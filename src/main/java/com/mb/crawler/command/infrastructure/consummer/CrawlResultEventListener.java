package com.mb.crawler.command.infrastructure.consummer;

import com.mb.crawler.command.api.CrawlCommandApi;
import com.mb.crawler.events.CrawlResultEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CrawlResultEventListener {
    private final CrawlCommandApi crawlCommandApi;

    @EventListener
    public void onPageEvent(CrawlResultEvent event) {
        try {
            log.debug("On page result event {}", event);

            if (event.externalLinks() == null) {
                return;
            }

            event.externalLinks().forEach(url -> {
                boolean submit = crawlCommandApi.submit(url);
                log.debug("Published {} new crawl commands from {}", submit, url);
            });
        } catch (Exception e) {
            //A metric and alert should be created
            log.warn("Could not handle event {}", event, e);
        }
    }
}
