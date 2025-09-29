package com.mb.crawler.display;

import com.mb.crawler.events.CrawlResultEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class DisplayListener {

    @EventListener
    @Async("displayExec")
    public void onPageResultEvent(CrawlResultEvent event) {
        log.info("Page result: {}", event);
    }
}
