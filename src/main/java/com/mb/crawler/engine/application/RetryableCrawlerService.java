package com.mb.crawler.engine.application;

import com.mb.crawler.catalog.VisitedPages;
import com.mb.crawler.engine.api.CrawlResult;
import com.mb.crawler.engine.api.CrawlerApi;
import com.mb.crawler.engine.api.PageToCrawl;
import com.mb.crawler.engine.application.extractor.NonRetriablePageFetchException;
import com.mb.crawler.engine.application.extractor.PageFetchException;
import com.mb.crawler.engine.application.extractor.WebPageContentExtractor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class RetryableCrawlerService implements CrawlerApi {
    private final WebPageContentExtractor extractor;
    private final VisitedPages visitedPages;

    @Override
    @Retryable(
            retryFor = PageFetchException.class,
            noRetryFor = NonRetriablePageFetchException.class,
            maxAttempts = 4,
            backoff = @Backoff(delay = 10_000, multiplier = 4.0),
            listeners = {"crawlerRetryLogger"}
    )
    public Optional<CrawlResult> crawl(PageToCrawl pageToCrawl) {
        String url = pageToCrawl.url();

        if (!visitedPages.couldVisit(url)) {
            log.debug("Page[{}] was already crawled", url);
            return Optional.empty();
        }

        Set<String> pageUrls = extractor.extractLinks(url);

        return Optional.of(new CrawlResult(url, pageUrls));
    }
}
