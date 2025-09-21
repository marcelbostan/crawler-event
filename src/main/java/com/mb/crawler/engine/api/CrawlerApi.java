package com.mb.crawler.engine.api;

import java.util.Optional;

public interface CrawlerApi {
    Optional<CrawlResult> crawl(PageToCrawl pageToCrawl);
}
