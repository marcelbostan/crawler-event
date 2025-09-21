package com.mb.crawler.command.application;

import com.mb.crawler.catalog.VisitedPages;
import com.mb.crawler.command.api.CrawlCommandApi;
import com.mb.crawler.events.PageToCrawlEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.util.function.Predicate.not;

@Component
@RequiredArgsConstructor
public class CrawlCommandService implements CrawlCommandApi {
    private final UrlFilter urlFilter;
    private final VisitedPages visitedPages;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public boolean submit(String url) {
        return Optional.ofNullable(url)
                .filter(urlFilter::isSameDomain)
                .filter(urlFilter::looksLikeWebPage)
                .filter(not(visitedPages::isVisited))
                .map(this::publish)
                .orElse(false);
    }

    private boolean publish(String url) {
        eventPublisher.publishEvent(new PageToCrawlEvent(url));
        return true;
    }
}
