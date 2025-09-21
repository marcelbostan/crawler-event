package com.mb.crawler.engine.infrastructure.producer;

import java.util.Set;

public record CrawlResultEvent(String pageUrl,
                               Set<String> externalLinks) {
}
