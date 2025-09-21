package com.mb.crawler.engine.application.extractor;

public class NonRetriablePageFetchException extends RuntimeException {
    public NonRetriablePageFetchException(String message) {
        super(message);
    }

}
