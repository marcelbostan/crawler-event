package com.mb.crawler.events;

import java.util.Set;

public record CrawlResultEvent(String pageUrl,
                               Set<String> externalLinks) {
}
