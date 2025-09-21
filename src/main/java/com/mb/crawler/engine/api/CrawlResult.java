package com.mb.crawler.engine.api;

import java.util.Set;

public record CrawlResult(String originLink,
                          Set<String> links) {
}
